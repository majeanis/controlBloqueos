package cl.cerrocolorado.recob.bo.beans;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cl.cerrocolorado.recob.bo.ResponsableBO;
import cl.cerrocolorado.recob.po.EmpresaPO;
import cl.cerrocolorado.recob.po.ResponsablePO;
import cl.cerrocolorado.recob.po.TrabajadorPO;
import cl.cerrocolorado.recob.po.UbicacionPO;
import cl.cerrocolorado.recob.to.entidades.EmpresaTO;
import cl.cerrocolorado.recob.to.entidades.ResponsableTO;
import cl.cerrocolorado.recob.to.entidades.TrabajadorTO;
import cl.cerrocolorado.recob.to.entidades.UbicacionTO;
import cl.cerrocolorado.recob.utils.FechaUtils;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ResultadoProceso;
import cl.cerrocolorado.recob.utils.ToStringUtils;
import cl.cerrocolorado.recob.utils.Transaccional;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author mauricio.camara
 */
@Scope("singleton")
@Service("responsableBO")
public class ResponsableBean implements ResponsableBO
{
    private static final Logger logger = LogManager.getLogger(ResponsableBean.class);
    
    @Autowired
    private ResponsablePO responsablePO;

    @Autowired
    private EmpresaPO empresaPO;

    @Autowired
    private TrabajadorPO trabajadorPO;

    @Autowired
    private UbicacionPO ubicacionPO;
    
    @Override
    @Transaccional
    public Respuesta<ResponsableTO> save(ResponsableTO responsable)
    {
        logger.info ("save[INI] responsable: {}", responsable);
        
        Resultado rtdo = new ResultadoProceso();

        if( responsable == null )
        {
        	rtdo.addError( this.getClass(), "Debe informar los datos del Responsable");
            logger.info ("save[FIN] responsable informado en null" );        	
            return Respuesta.of(rtdo);
        } 
        if(responsable.getFechaIngreso() != null)
        {
            rtdo.addError(this.getClass(), "No debe informar Fecha de Ingreso [%s]", ToStringUtils.toString(responsable.getFechaIngreso(),true) );
        }
        if(responsable.getFechaSalida() != null)
        {
            rtdo.addError(this.getClass(), "No debe informar Fecha de Salida [%s]", ToStringUtils.toString(responsable.getFechaSalida(),true) );
        }
        if(responsable.getUbicacion()==null || responsable.getUbicacion().isKeyBlank())
        {
            rtdo.addError(this.getClass(), "Responsable debe estar asociado a una Ubicación" );
        }
        else
        {
		    UbicacionTO ubicacion = ubicacionPO.get(responsable.getUbicacion());
		    if( ubicacion == null )
		    {
		        rtdo.addError( this.getClass(), "La ubicación informada no existe [id: %d]", responsable.getUbicacion().getId());
		    }
            else
            {
                responsable.setUbicacion(ubicacion);
            }
        }
        if ( responsable.getEmpresa() == null || responsable.getEmpresa().isKeyBlank() )
        {
            rtdo.addError( this.getClass(), "Debe informar la empresa asociada al Trabajador");
        }
        else
        {
            EmpresaTO empr = empresaPO.get(responsable.getEmpresa());
            if( empr == null )
            {
                rtdo.addError(this.getClass(), "Empresa informada no existe [RUT: %s]", responsable.getEmpresa().getRut().toText() );
            } else
            {
                responsable.setEmpresa(empr);
            }
        }
        if( responsable.getPersona() == null || responsable.getPersona().isKeyBlank())
        {
            rtdo.addError( this.getClass(), "Debe informar el Trabajador");
        }
        else if( responsable.getEmpresa() != null)
        {
            TrabajadorTO aux = new TrabajadorTO();
            aux.setEmpresa(responsable.getEmpresa());
            aux.setRut(responsable.getPersona().getRut());
            
            TrabajadorTO trab = trabajadorPO.get(aux);
            if( trab == null)
            {
                rtdo.addError(this.getClass(), "Trabajador informado no existe [RUT: %s]", aux.getRut().toText());
            } else
            {
                responsable.setPersona(trab);
            }
        }

        // Solo es posible agregar nuevos Responsables
        ResponsableTO actual = responsablePO.getVigente(responsable.getUbicacion());
        logger.debug("save[001] después de obtener al responsable actual: actual{}-responsable{}", actual, responsable);
        
        if(actual != null &&
           responsable.getEmpresa() != null &&
           responsable.getPersona() != null &&
           Objects.equals(actual.getEmpresa().getId(), responsable.getEmpresa().getId()) &&
           Objects.equals(actual.getPersona().getId(), responsable.getPersona().getId()) )
        {
            rtdo.addError(this.getClass(), "Trabajador [%s] ya es el responsable actual", responsable.getPersona().getRut().toText());
        }

        logger.debug("save[002] resultado de validaciones: {}", rtdo);
        if( !rtdo.esExitoso() )
        {
            logger.info ("save[FIN] salida por errores de validación: {}", rtdo );
            return Respuesta.of(rtdo);
        }

        // Guardamos la salida del Responsable actual
        if (actual != null)
        {
            actual.setFechaSalida( new Date() );
            responsablePO.save(actual);
            logger.debug("save[003] después de guardar la salida en el responsable actual: {}", actual);
        }

        responsable.setFechaIngeso( new Date() );
        responsablePO.save(responsable);
        logger.debug("save[004] después de guardar al responsable: {}", responsable);
        
        rtdo.addMensaje(this.getClass(), "Responsable guardado con éxito");
        
        logger.info ("save[FIN] responsable guardado con exito: {}", responsable );
        return Respuesta.of(rtdo, responsable);
    }
    
    @Transaccional
    @Override
    public Respuesta<ResponsableTO> delete(ResponsableTO pk) throws Exception
    {
        logger.info ("delete[INI] pkResponsable: {}", pk );
        Resultado rtdo = new ResultadoProceso();
        
        if( pk == null || pk.isKeyBlank() )
        {
        	rtdo.addError(this.getClass(), "Debe informar la identificación del Responsable" );
        	logger.info( "delete[FIN] no se informaron todos los filtros: {}", pk );
        	return Respuesta.of(rtdo);
        }
        
        ResponsableTO resp = responsablePO.get(pk);
        if( resp == null )
        {
            rtdo.addError(this.getClass(), "No existe Responsable %s", pk.getPersona().getRut().toText() );
            logger.info ("delete[FIN] no existe el responsable: {}", pk );
            return Respuesta.of(rtdo);
        } 
        
        if(!responsablePO.isDeleteable(resp))
        {
            rtdo.addError(this.getClass(), "Responsable tiene registros asociados" );
            logger.info ("delete[FIN] registro no puede ser eliminado");
            return Respuesta.of(rtdo);
        }

        responsablePO.delete(resp);
        logger.debug("delete[001] despues de eliminar al Responsanle: {}", resp );
        
        rtdo.addMensaje(this.getClass(), "Responsable %s eliminado con éxito", resp.getEmpresa().getRut().toText() );
        logger.info ("delete[FIN] responsable eliminado con exito: {} {}", rtdo, resp );
        return Respuesta.of(rtdo,resp);
    }
    
    @Override
    public Respuesta<ResponsableTO> getVigente(UbicacionTO pk)
    {
        logger.info ("getVigente[INI] pkUbicacion: {}", pk );

        Resultado rtdo = new ResultadoProceso();
        if(pk == null || pk.isKeyBlank())
        {
            rtdo.addError(this.getClass(), "Debe informar la identificación de la Ubicación" );
            logger.info("getVigente[FIN] No se informaron los datos mínimos: {}", pk );
            return Respuesta.of(rtdo);
        }

        ResponsableTO resp = responsablePO.getVigente(pk);
        if( resp == null )
        {
            rtdo.addMensaje(this.getClass(), "No se encontró Responsable Actual" );
        }

        logger.info ("getVigente[FIN] resultado búsqueda: {}", resp );
        return Respuesta.of(rtdo, resp);
    }

    @Override
    public Respuesta<ResponsableTO> get(ResponsableTO pk)
    {
        logger.info ("get[INI] pk: {}", pk);
        Resultado rtdo = new ResultadoProceso();
        
        if(pk == null || pk.isKeyBlank())
        {
            rtdo.addError(this.getClass(), "Debe informar la identificación del Responsable");
            logger.info ("get[FIN] no se informó la identificación del responsable: {}", pk);
            return Respuesta.of(rtdo);
        }
        
        ResponsableTO resp = responsablePO.get(pk);
        if(resp==null)
        {
            rtdo.addMensaje(this.getClass(), "No se encontró al Responsable %s", pk.getPersona().getRut().toText() );
        }

        logger.info ("get[FIN] responsable retornado: {}", resp);
        return Respuesta.of(rtdo, resp);
    }
}
