package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.utils.Transaccional;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.po.CandadoPO;
import cl.cerrocolorado.recob.po.TrabajadorPO;
import cl.cerrocolorado.recob.po.UbicacionPO;
import cl.cerrocolorado.recob.to.CandadoTO;
import cl.cerrocolorado.recob.to.PersonaTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.MensajeError;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ResultadoProceso;
import cl.cerrocolorado.recob.utils.Rut;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author mauricio.camara
 */
@Scope("singleton")
@Service("candadoBO")
public class CandadoBean implements CandadoBO
{
    private static final Logger logger = LogManager.getLogger(CandadoBean.class);
    
    @Autowired
    private CandadoPO candadoPO;

    @Autowired
    private UbicacionPO ubicacionPO;
    
    @Autowired
    private TrabajadorPO trabajadorPO;

    @Transaccional
    @Override
    public Respuesta<CandadoTO> guardar(CandadoTO candado) throws MensajeError
    {
        logger.info ("guardar[INI] candadoo: {}", candado);
        
        Resultado rtdo = new ResultadoProceso();

        if ( candado.getVigente() == null )
        {
            rtdo.addError(CandadoBean.class, "Debe informar vigencia del candado");
        }
        
        if( StringUtils.isBlank(candado.getSerie()) )
        {
            rtdo.addError(CandadoBean.class, "Debe informar número de serie del candado" );
        }
        
        if( candado.getPersona() == null )
        {
            rtdo.addError(CandadoBean.class, "Debe asociar el candado a una Persona" );
        } else if( Rut.isBlank(candado.getPersona().getRut() ) )
        {
            rtdo.addError(CandadoBean.class, "Debe informar el RUT de la Persona");
        }
        
        if( candado.getUbicacion() == null )
        {
            rtdo.addError(CandadoBean.class, "Candado debe estar asociado a una ubicación" );
        } else if (candado.getUbicacion().getId() == null )
        {
            rtdo.addError(CandadoBean.class, "Candado debe estar asociado a una ubicación" );
        }

        if( candado.getUso() == null )
        {
            rtdo.addError(CandadoBean.class, "Debe informar el uso del candado" );
        } else if (candado.getUso().getId() == null )
        {
            rtdo.addError(CandadoBean.class, "Debe informar el uso del candado" );
        }

        UbicacionTO ubicacion = ubicacionPO.get(candado.getUbicacion());
        if( ubicacion == null )
        {
            rtdo.addError( CajaBloqueoBean.class, "La ubicación informada no existe [id: #{1}]", String.valueOf(candado.getUbicacion().getId()));
        }
        // Validamos que exista el RUT de la Personas Informada
        PersonaTO persona = trabajadorPO.getPersona(candado.getPersona());
        if( persona == null )
        {
            rtdo.addError(CandadoBean.class, "Persona informada no existe [RUT: #{1}]", candado.getPersona().getRut().toText());
        }
        
        if( !rtdo.esExitoso() )
        {
            logger.info ("guardar[FIN] saliendo del método por falla en validaciones: {}", rtdo );
            return new Respuesta<>(rtdo);
        }

        // Buscamoa la preexistencia de la caja
        CandadoTO otroCandado = candadoPO.get( candado );
        if( otroCandado != null ) 
        {
            logger.debug("guardar[001] ya existe registro para el candado: {}", otroCandado );
            candado.setId(otroCandado.getId());
        }

        // Si llegamos a este punto la Caja puede ser Guardada
        candadoPO.guardar(candado);
        logger.info ("guardar[FIN] candado guardado con exito: {}", candado );
        return new Respuesta<>(candado);
    }
    
    @Transaccional
    @Override
    public Resultado eliminar(CandadoTO pkCandado) throws MensajeError
    {
        logger.info ("eliminar[INI] candado: {}", pkCandado );
        Resultado rtdo = new ResultadoProceso();
        
        if( StringUtils.isBlank(pkCandado.getSerie()) )
        {
            rtdo.addError(CandadoBean.class, "Debe informar el número de serie del candado" );
        }
        if( pkCandado.getUbicacion() == null )
        {
            rtdo.addError(CandadoBean.class, "Debe informar la ubicación del candado" );
        } else if( pkCandado.getUbicacion().getId() == null )
        {
            rtdo.addError(CandadoBean.class, "Debe informar la ubicación del candado" );
        }
        
        CandadoTO candado = candadoPO.get(pkCandado);
        
        if( candado == null )
        {
            rtdo.addError(CandadoBean.class, "No existe Candado con número de serie #{1}", pkCandado.getSerie() );
            logger.info ("eliminar[FIN] no existe candado: {}", pkCandado );
            return rtdo;
        }
        
        // Si llegamos a este punto, entonces es posible la eliminación
        candadoPO.eliminar(pkCandado);
        logger.debug("eliminar[001] despues de eliminar el candado: {}", pkCandado );
        
        rtdo.addMensaje(CandadoBean.class, "Candado con número de serie #{1} eliminado con éxito", pkCandado.getSerie() );
        logger.info ("eliminar[FIN] candado eliminado con exito: {} {}", rtdo, candado );
        return rtdo;
    }
    
    @Override
    public CandadoTO get(CandadoTO pkCandado)
    {
        logger.info ("get[INI] candado: {}", pkCandado );

        CandadoTO candado = candadoPO.get(pkCandado);
        
        logger.info ("get[FIN] resultado busqueda: {}", candado );
        return candado;
    }

    @Override
    public List<CandadoTO> get(UbicacionTO pkUbicacion)
    {
        logger.info ("get[INI] ubicacion: {}", pkUbicacion );

        List<CandadoTO> candados = candadoPO.get(pkUbicacion, true);
        
        logger.info ("get[FIN] cantidad registros encontrados: {}", candados.size() );
        return candados;
    }

    @Override
    public List<CandadoTO> get(UbicacionTO pkUbicacion, PersonaTO pkPersona)
    {
        logger.info ("get[INI] ubicacion: {}", pkUbicacion );
        logger.info ("get[INI] persona: {}", pkPersona );
        
        List<CandadoTO> candados = candadoPO.get(pkUbicacion, pkPersona);
        
        logger.info ("get[FIN] cantidad registros encontrados: {}", candados.size() );
        return candados;
    }

    @Override
    public List<CandadoTO> getAll(UbicacionTO pkUbicacion)
    {
        logger.info ("getAll[INI] ubicacion: {}", pkUbicacion );

        List<CandadoTO> candados = candadoPO.get(pkUbicacion, (Boolean) null);
        
        logger.info ("getAll[FIN] cantidad registros encontrados: {}", candados.size() );
        return candados;
    }
}
