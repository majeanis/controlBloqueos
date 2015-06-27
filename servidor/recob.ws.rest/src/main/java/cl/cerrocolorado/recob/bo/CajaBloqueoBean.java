package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.utils.Transaccional;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.po.CajaBloqueoPO;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.MensajeError;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ResultadoProceso;
import java.util.List;
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
@Service("cajaBloqueoBO")
public class CajaBloqueoBean implements CajaBloqueoBO
{
    private static final Logger logger = LogManager.getLogger(CajaBloqueoBean.class);
    
    @Autowired
    private CajaBloqueoPO cajaPO;

    @Transaccional
    @Override
    public Respuesta<CajaBloqueoTO> guardar(CajaBloqueoTO cajaBloqueo) throws MensajeError
    {
        logger.info ("guardar[INI] cajaBloqueo: {}", cajaBloqueo);
        
        Resultado rtdo = new ResultadoProceso();

        if ( cajaBloqueo.getVigente() == null )
        {
            rtdo.addError( CajaBloqueoBean.class, "Debe informar Vigencia de la Caja");
        }
        
        if( cajaBloqueo.getNumero() == null )
        {
            rtdo.addError( CajaBloqueoBean.class, "Debe informar Número de la Caja" );
        }
        
        if( cajaBloqueo.getNombre() == null )
        {
            rtdo.addError( CajaBloqueoBean.class, "Debe informar nombre de la Caja" );
        }
        
        if( cajaBloqueo.getUbicacion() == null )
        {
            rtdo.addError( CajaBloqueoBean.class, "Caja debe estar asociada a una Ubicación" );
        } else if (cajaBloqueo.getUbicacion().getId() == null )
        {
            rtdo.addError( CajaBloqueoBean.class, "Caja debe estar asociada a una Ubicación" );
        }

        if( !rtdo.esExitoso() )
        {
            logger.info ("guardar[FIN] saliendo del método por campos en NULL: {}", rtdo );
            return new Respuesta<>(rtdo);
        }
        
        // Buscamoa la preexistencia de la caja
        CajaBloqueoTO otraCaja = cajaPO.get( cajaBloqueo );
        if( otraCaja != null ) 
        {
            logger.debug("guardar[001] ya existe registro para la caja: {}", otraCaja );
            cajaBloqueo.setId(otraCaja.getId());
        }

        // Si llegamos a este punto la Caja puede ser Guardada
        cajaPO.guardar(cajaBloqueo);
        logger.info ("guardar[FIN] caja guardada con exito: {}", cajaBloqueo );
        return new Respuesta<>(cajaBloqueo);
    }
    
    @Transaccional
    @Override
    public Respuesta<CajaBloqueoTO> eliminar(CajaBloqueoTO key) throws MensajeError
    {
        logger.info ("eliminar[INI] caja: {}", key );
        Resultado rtdo = new ResultadoProceso();
        
        if( key.getNumero() == null )
        {
            rtdo.addError(CajaBloqueoBean.class, "Debe informar el N° de la Caja" );
        }
        if( key.getUbicacion() == null )
        {
            rtdo.addError(CajaBloqueoBean.class, "Debe informar la Ubicación de la Caja" );
        } else if( key.getUbicacion().getId() == null )
        {
            rtdo.addError(CajaBloqueoBean.class, "Debe informar la Ubicación de la Caja" );
        }
        
        CajaBloqueoTO caja = cajaPO.get(key);
        
        if( caja == null )
        {
            rtdo.addError(CajaBloqueoBean.class, "No existe Caja N° #{1}", String.valueOf( key.getNumero() ) );
            logger.info ("eliminar[FIN] no existe caja: {}", key );
            return new Respuesta<>(rtdo);
        }
        
        cajaPO.eliminar(caja);
        logger.debug("eliminar[001] despues de eliminar la caja: {}", caja );
        
        rtdo.addMensaje(CajaBloqueoBean.class, "Caja N° #{1} eliminada con éxito", String.valueOf( caja.getNumero() ) );
        logger.info ("eliminar[FIN] caja eliminada con exito: {} {}", rtdo, caja );
        return new Respuesta<>(rtdo);
    }
    
    @Override
    public CajaBloqueoTO get(CajaBloqueoTO key)
    {
        logger.info ("get[INI] caja: {}", key );

        CajaBloqueoTO caja = cajaPO.get(key);
        
        logger.info ("get[FIN] resultado busqueda: {}", caja );
        return caja;
    }

    @Override
    public List<CajaBloqueoTO> get(UbicacionTO ubicacion)
    {
        logger.info ("get[INI] ubicacion: {}", ubicacion );

        List<CajaBloqueoTO> cajas = cajaPO.get(ubicacion);
        
        logger.info ("get[FIN] cantidad registros encontrados: {}", cajas.size() );
        return cajas;
    }
}
