package cl.cerrocolorado.recob.po;

import cl.cerrocolorado.recob.po.map.RecobMap;
import cl.cerrocolorado.recob.to.CandadoTO;
import cl.cerrocolorado.recob.to.PersonaTO;
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
public class UbicacionPO
{
    private static final Logger logger = LogManager.getLogger(UbicacionPO.class );
    
    @Autowired
    private RecobMap mapper;
    
    public UbicacionTO get(UbicacionTO pkUbicacion)
    {
        logger.info ("get[INI] pkUbicacion: {}", pkUbicacion );
        
        UbicacionTO ubicacion = mapper.selectUbicacion(pkUbicacion);
        logger.debug("get[001] despues de hacer el select: {}", ubicacion );
        
        logger.info ("get[FIN] registro encontrado: {}", ubicacion);
        return ubicacion;
    }
}
