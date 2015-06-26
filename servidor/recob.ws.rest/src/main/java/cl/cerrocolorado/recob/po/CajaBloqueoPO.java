package cl.cerrocolorado.recob.po;

import cl.cerrocolorado.recob.po.map.RecobMap;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
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
    
    public CajaBloqueo getCajaBloqueo(int cajaId)
    {
        CajaBloqueoTO caja = mapper.selectCajaBloqueo( params );
        
    }
}
