package cl.cerrocolorado.recob.po;

import cl.cerrocolorado.recob.po.map.RecobMap;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mauricio.camara
 */
@Scope("singleton")
@Repository
public class CajaBloqueoPO
{
    private static final Logger logger = LogManager.getLogger( CajaBloqueoPO.class );
    
    @Autowired
    private RecobMap mapper;
    
    public CajaBloqueoTO guardar(CajaBloqueoTO caja)
    {
        logger.info ("guardar[INI] caja: {}", caja);
        
        if( caja.getId() == null )
        {
            mapper.insertCajaBloqueo( caja );
            logger.debug("guardar[001] despues de insertar la caja: {}", caja);
        } else
        {
            mapper.updateCajaBloqueo( caja );
            logger.debug("guardar[002] despues de actualizar la caja: {}", caja);
        }
        
        logger.info ("guardar[FIN] caja: {}", caja);
        return caja;
    }

    public void eliminar(CajaBloqueoTO pkCaja)
    {
        logger.info ("eliminar[INI] pkCaja: {}", pkCaja );
        mapper.deleteCajaBloqueo(pkCaja);
        logger.info ("eliminar[FIN] despues de eliminar a la caja: {}", pkCaja );
    }

    public CajaBloqueoTO get(CajaBloqueoTO pkCaja)
    {
        logger.info ("get[INI] pkCaja: {}", pkCaja );
        
        Map<String, Object> params = new HashMap<>();
        params.put( "ubicacion", pkCaja.getUbicacion() );
        params.put( "caja", pkCaja );
        logger.debug("get[001] parametros: {}", params);
        
        List<CajaBloqueoTO> cajas = mapper.selectCajasBloqueos( params );
        logger.debug("get[002] despues de ejecutar el select: {}", cajas.size() );
        
        if(cajas.isEmpty())
        {
            logger.info ("get[FIN] no se encontró registro de la caja: {}", params );
            return null;
        }
        
        logger.info ("get[FIN] caja encontrada: {}", cajas.get(0) );
        return cajas.get(0);
    }
    
    public List<CajaBloqueoTO> get(UbicacionTO pkUbicacion, Boolean vigencia)
    {
        logger.info ("get[INI] pkUbicacion: {}", pkUbicacion);
        logger.info ("get[INI] vigencia: {}", vigencia );
        
        Map<String, Object> parms = new HashMap<>();
        parms.put("ubicacion", pkUbicacion);
        parms.put("vigencia", vigencia);
        logger.debug("get[001] parametros: {}", parms);

        List<CajaBloqueoTO> cajas = mapper.selectCajasBloqueos( parms );
        logger.debug("get[002] despues de ejecutar el select: {}", cajas.size() );

        logger.info ("get[FIN] registros encontrados: {}", cajas.size() );
        return cajas;
    }
}
