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
import cl.cerrocolorado.recob.to.EmpresaTO;
import cl.cerrocolorado.recob.to.ResponsableTO;
import cl.cerrocolorado.recob.to.TrabajadorTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ResultadoProceso;
import cl.cerrocolorado.recob.utils.Transaccional;
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

    private Respuesta<ResponsableTO> guardar(ResponsableTO responsable, boolean esNuevo)
    {
        logger.info ("guardar[INI] responsable: {}", responsable);
        
        Resultado rtdo = new ResultadoProceso();

        if( responsable == null )
        {
        	rtdo.addError( this.getClass(), "Debe informar los datos del Responsable");
            logger.info ("guardar[FIN] responsable informado en null" );        	
            return Respuesta.of(rtdo);
        } 
        if(responsable.getUbicacion()==null)
        {
            rtdo.addError(this.getClass(), "Debe informa la Ubicación");
        }
        if( responsable.getFechaIngreso() == null)
        {
            rtdo.addError(this.getClass(), "Debe informar la fecha de ingreso" );
        }
        if ( responsable.getEmpresa() == null || responsable.getEmpresa().isKeyBlank() )
        {
            rtdo.addError( this.getClass(), "Debe informar la empresa asociada al Trabajador");
        } else
        {
            EmpresaTO empr = empresaPO.obtener(responsable.getEmpresa());
            if( empr == null )
            {
                rtdo.addError(this.getClass(), "Empresa informada no existe");
            } else
            {
                responsable.setEmpresa(empr);
            }
        }
        if( responsable.getPersona() == null || responsable.getPersona().isKeyBlank())
        {
            rtdo.addError( this.getClass(), "Debe informar el Trabajador");
        } else if( responsable.getEmpresa() != null)
        {
            TrabajadorTO aux = new TrabajadorTO();
            aux.setEmpresa(responsable.getEmpresa());
            aux.setRut(responsable.getPersona().getRut());
            
            TrabajadorTO trab = trabajadorPO.obtener(aux);
            if( trab == null)
            {
                rtdo.addError(this.getClass(), "Trabajador informado no existe");
            } else
            {
                responsable.setPersona(trab);
            }
        }

        if( !rtdo.esExitoso() )
        {
            logger.info ("guardar[FIN] hubo fallas de validación: {}", rtdo );
            return Respuesta.of(rtdo);
        }

        // Buscamos la preexistencia del registro
        ResponsableTO actual = responsablePO.getVigente(responsable.getUbicacion());
        if(esNuevo)
        {
            if(Objects.equals(actual.getEmpresa().getId(), responsable.getEmpresa().getId()) &&
               Objects.equals(actual.getPersona().getId(), responsable.getPersona().getId()) )
            {
                rtdo.addError(this.getClass(), "Trabajador [#{1}] ya es el responsable actual", responsable.getPersona().getRut().toText());
            }
            else
            {
                // como se trata de un nuevo turno, nos aseguramos de darle la salida al anterior
                actual.setFechaSalida(responsable.getFechaIngreso());
                responsablePO.guardar(actual);
            }
        }
        else if( actual == null)
        {
            rtdo.addError(this.getClass(), "Trabajador [#{1}] no es el Responsable actual", responsable.getPersona().getRut().toText());
        }
        else if( !Objects.equals(actual.getEmpresa().getId(), responsable.getEmpresa().getId()) ||
                 !Objects.equals(actual.getPersona().getId(), responsable.getPersona().getId()))
        {
            rtdo.addError(this.getClass(), "Trabajador [#{1}] no es el responsable actual", responsable.getPersona().getRut().toText());
        }
        else
        {
            responsable.setId(actual.getId());
            responsable.setFechaIngeso(actual.getFechaIngreso());
            responsable.setEmpresa(actual.getEmpresa());
            responsable.setPersona(actual.getPersona());
            responsable.setUbicacion(actual.getUbicacion());
        }

        if( !rtdo.esExitoso() )
        {
            logger.info ("guardar[FIN] salida por errores de validación: {}", rtdo );
            return Respuesta.of(rtdo);
        }

        // Si llegamos a este punto el Responsable puede ser Guardado
        responsablePO.guardar(responsable);
        rtdo.addMensaje(this.getClass(), "Responsable guardado con éxito");
        
        logger.info ("guardar[FIN] responsable guardado con exito: {}", responsable );
        return Respuesta.of(rtdo, responsable);
    }

    @Override
    @Transaccional
    public Respuesta<ResponsableTO> crear(ResponsableTO datos) throws Exception
    {
        return guardar(datos,true);
    }

    @Override
    @Transaccional
    public Respuesta<ResponsableTO> modificar(ResponsableTO datos) throws Exception
    {
        return guardar(datos,false);
    }
    
    @Transaccional
    @Override
    public Respuesta<ResponsableTO> eliminar(ResponsableTO pk) throws Exception
    {
        logger.info ("eliminar[INI] pkResponsable: {}", pk );
        Resultado rtdo = new ResultadoProceso();
        
        if( pk == null || pk.isKeyBlank() )
        {
        	rtdo.addError(this.getClass(), "Debe informar la identificación del Responsable" );
        	logger.info( "eliminar[FIN] no se informaron todos los filtros: {}", pk );
        	return Respuesta.of(rtdo);
        }
        
        ResponsableTO resp = responsablePO.obtener(pk);
        if( resp == null )
        {
            rtdo.addError(this.getClass(), "No existe Responsable #{1}", pk.getPersona().getRut().toText() );
            logger.info ("eliminar[FIN] no existe el responsable: {}", pk );
            return Respuesta.of(rtdo);
        } 
        
        if(!responsablePO.esEliminable(resp))
        {
            rtdo.addError(this.getClass(), "Responsable tiene registros asociados" );
            logger.info ("eliminar[FIN] registro no puede ser eliminado");
            return Respuesta.of(rtdo);
        }

        responsablePO.eliminar(resp);
        logger.debug("eliminar[001] despues de eliminar al Responsanle: {}", resp );
        
        rtdo.addMensaje(this.getClass(), "Responsable #{1} eliminada con éxito", resp.getEmpresa().getRut().toText() );
        logger.info ("eliminar[FIN] responsable eliminado con exito: {} {}", rtdo, resp );
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
        
        ResponsableTO resp = responsablePO.obtener(pk);
        if(resp==null)
        {
            rtdo.addMensaje(this.getClass(), "No se encontró al Responsable [#{1}]", pk.getPersona().getRut().toText() );
        }

        logger.info ("get[FIN] responsable retornado: {}", resp);
        return Respuesta.of(rtdo, resp);
    }
}
