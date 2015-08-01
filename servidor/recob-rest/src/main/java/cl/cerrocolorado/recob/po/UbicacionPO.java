package cl.cerrocolorado.recob.po;

import cl.cerrocolorado.recob.po.map.RecobMap;
import cl.cerrocolorado.recob.to.FuncionBloqueoTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    public List<FuncionBloqueoTO> getFunciones(Optional<Boolean> vigencia)
    {
        logger.info ("getFunciones[INI] vigencia: {}", vigencia);

        Map<String,Object> parms = new HashMap<>();
        parms.put("vigente", vigencia.orElse(null));
        List<FuncionBloqueoTO> lista = mapper.selectFuncionesBloqueo(parms);
        
        logger.info ("getFunciones[FIN] registros retornados: {}", lista.size());
        return lista;
    }
}
