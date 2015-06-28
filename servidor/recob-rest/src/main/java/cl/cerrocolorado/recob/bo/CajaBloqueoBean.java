package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.utils.Transaccional;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.po.CajaBloqueoPO;
import cl.cerrocolorado.recob.po.UbicacionPO;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
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

    @Autowired
    private UbicacionPO ubicacionPO;

    @Transaccional
    @Override
    public Respuesta<CajaBloqueoTO> guardar(CajaBloqueoTO cajaBloqueo)
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

        UbicacionTO ubicacion = ubicacionPO.get(cajaBloqueo.getUbicacion());
        if( ubicacion == null )
        {
            rtdo.addError( CajaBloqueoBean.class, "La ubicación informada no existe [id: #{1}]", String.valueOf(cajaBloqueo.getUbicacion().getId()));
        }

        if( !rtdo.esExitoso() )
        {
            logger.info ("guardar[FIN] saliendo del método por campos en NULL: {}", rtdo );
            return new Respuesta<>(rtdo);
        }
        
        // Buscamos la preexistencia de la caja
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
    public Resultado eliminar(CajaBloqueoTO pkCaja)
    {
        logger.info ("eliminar[INI] caja: {}", pkCaja );
        Resultado rtdo = new ResultadoProceso();
        
        if( pkCaja.getNumero() == null )
        {
            rtdo.addError(CajaBloqueoBean.class, "Debe informar el N° de la Caja" );
        }
        if( pkCaja.getUbicacion() == null )
        {
            rtdo.addError(CajaBloqueoBean.class, "Debe informar la Ubicación de la Caja" );
        } else if( pkCaja.getUbicacion().getId() == null )
        {
            rtdo.addError(CajaBloqueoBean.class, "Debe informar la Ubicación de la Caja" );
        }
        
        CajaBloqueoTO caja = cajaPO.get(pkCaja);
        
        if( caja == null )
        {
            rtdo.addError(CajaBloqueoBean.class, "No existe Caja N° #{1}", String.valueOf( pkCaja.getNumero() ) );
            logger.info ("eliminar[FIN] no existe caja: {}", pkCaja );
            return rtdo;
        }
        
        cajaPO.eliminar(caja);
        logger.debug("eliminar[001] despues de eliminar la caja: {}", caja );
        
        rtdo.addMensaje(CajaBloqueoBean.class, "Caja N° #{1} eliminada con éxito", String.valueOf( caja.getNumero() ) );
        logger.info ("eliminar[FIN] caja eliminada con exito: {} {}", rtdo, caja );
        return rtdo;
    }
    
    @Override
    public CajaBloqueoTO get(CajaBloqueoTO pkCaja)
    {
        logger.info ("get[INI] caja: {}", pkCaja );

        CajaBloqueoTO caja = cajaPO.get(pkCaja);
        
        logger.info ("get[FIN] resultado busqueda: {}", caja );
        return caja;
    }

    @Override
    public List<CajaBloqueoTO> getVigentes(UbicacionTO pkUbicacion)
    {
        logger.info ("getVigentes[INI] ubicacion: {}", pkUbicacion );

        List<CajaBloqueoTO> cajas = cajaPO.get(pkUbicacion, true);
        
        logger.info ("getVigentes[FIN] cantidad registros encontrados: {}", cajas.size() );
        return cajas;
    }

    @Override
    public List<CajaBloqueoTO> getTodos(UbicacionTO pkUbicacion)
    {
        logger.info ("getTodos[INI] ubicacion: {}", pkUbicacion );

        List<CajaBloqueoTO> cajas = cajaPO.get(pkUbicacion, null);
        
        logger.info ("getTodos[FIN] cantidad registros encontrados: {}", cajas.size() );
        return cajas;
    }
}
