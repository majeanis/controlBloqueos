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
import org.springframework.stereotype.Repository;

/**
 *
 * @author mauricio.camara
 */
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

    public void eliminar(CajaBloqueoTO caja)
    {
        logger.info ("eliminar[INI] caja: {}", caja );
        mapper.deleteCajaBloqueo(caja);
        logger.info ("eliminar[FIN] despues de eliminar a la caja: {}", caja );
    }

    public CajaBloqueoTO get(CajaBloqueoTO caja)
    {
        logger.info ("get[INI] caja: {}", caja );
        
        Map<String, Object> params = new HashMap<>();
        params.put( "ubicacion", caja.getUbicacion() );
        params.put( "caja", caja );
        logger.debug("get[001] parametros: {}", params);
        
        List<CajaBloqueoTO> cajas = (List<CajaBloqueoTO>) mapper.selectCajasBloqueos( params );
        logger.debug("get[002] despues de ejecutar el select: {}", cajas.size() );
        
        if(cajas.isEmpty())
        {
            logger.info ("get[FIN] no se encontró registro de la caja: {}", params );
            return null;
        }
        
        logger.info ("get[FIN] caja encontrada: {}", cajas.get(0) );
        return cajas.get(0);
    }
    
    public List<CajaBloqueoTO> get(UbicacionTO ubicacion)
    {
        logger.info ("get[INI] ubicacion: {}", ubicacion);
        
        Map<String, Object> params = new HashMap<>();
        params.put("ubicacion", ubicacion);
        logger.debug("get[001] parametros: {}", params);

        List<CajaBloqueoTO> cajas = (List<CajaBloqueoTO>) mapper.selectCajasBloqueos( params );
        logger.debug("get[002] despues de ejecutar el select: {}", cajas.size() );

        logger.info ("get[FIN] cajas: {}", cajas );
        return cajas;
    }
}
