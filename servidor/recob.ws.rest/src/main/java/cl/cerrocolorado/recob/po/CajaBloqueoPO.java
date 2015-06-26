package cl.cerrocolorado.recob.po;

import cl.cerrocolorado.recob.po.map.RecobMap;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
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
    private static Logger logger = LogManager.getLogger( CajaBloqueoPO.class );
    
    @Autowired
    private RecobMap mapper;
    
    public CajaBloqueoTO guardarCajaBloqueo(CajaBloqueoTO caja)
    {
        logger.info ("guardarCajaBloqueo[INI] caja: {}", caja);
        
        if( caja.getId() == null )
        {
            mapper.insertCajaBloqueo( caja );
            logger.debug("guardarCajaBloqueo[001] despues de insertar la caja: {}", caja);
        } else
        {
            mapper.updateCajaBloqueo( caja );
            logger.debug("guardarCajaBloqueo[002] despues de actualizar la caja: {}", caja);
        }
        
        logger.info ("guardarCajaBloqueo[FIN] caja: {}", caja);
        return caja;
    }
    
    public CajaBloqueoTO getCajaBloqueo(int idCaja)
    {
        logger.info ("getCajaBloqueo[INI] idCaja: {}", idCaja);
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idCaja", idCaja);
        logger.debug ("getCajaBloqueo[001] parametros: {}", params);
        
        List<CajaBloqueoTO> cajas = (List<CajaBloqueoTO>) mapper.selectCajasBloqueos( params );
        logger.debug ("getCajaBloqueo[002] despues de ejecutar el select: {}", cajas.size() );
        
        if(cajas.size() == 0)
        {
            logger.info ("getCajaBloqueo[FIN] no se encontró registro para Caja: {}", idCaja);
            return null;
        }
        
        CajaBloqueoTO caja = cajas.get(0);
        logger.info ("getCajaBloqueo[FIN] caja encontrada: {}", caja );
        return caja;
    }

    public CajaBloqueoTO getCajaBloqueo(int idUbicacion, int numeroCaja)
    {
        logger.info ("getCajaBloqueo[INI] idUbicacion: {}", idUbicacion);
        logger.info ("getCajaBloqueo[INI] numeroCaja: {}", numeroCaja);
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idUbicacion", idUbicacion);
        params.put("numeroCaja", numeroCaja);
        logger.debug ("getCajaBloqueo[001] parametros: {}", params);
        
        List<CajaBloqueoTO> cajas = (List<CajaBloqueoTO>) mapper.selectCajasBloqueos( params );
        logger.debug ("getCajaBloqueo[002] despues de ejecutar el select: {}", cajas.size() );
        
        if(cajas.size() == 0)
        {
            logger.info ("getCajaBloqueo[FIN] no se encontró registro para Caja: Ubicacion[{}] - Caja[{}]", idUbicacion, numeroCaja);
            return null;
        }
        
        CajaBloqueoTO caja = cajas.get(0);
        logger.info ("getCajaBloqueo[FIN] caja encontrada: {}", caja );
        return caja;
    }
    
    public List<CajaBloqueoTO> getCajasBloqueos(int idUbicacion)
    {
        logger.info ("getCajasBloqueos[INI] idUbicacion: {}", idUbicacion);
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idUbicacion", idUbicacion);
        logger.debug("getCajasBloqueos[001] parametros: {}", params);
        
        List<CajaBloqueoTO> cajas = (List<CajaBloqueoTO>) mapper.selectCajasBloqueos( params );
        logger.debug("getCajaBloqueo[002] despues de ejecutar el select: {}", cajas.size() );

        logger.info ("getCajaBloqueo[FIN] cajas: {}", cajas );
        return cajas;
    }
}
