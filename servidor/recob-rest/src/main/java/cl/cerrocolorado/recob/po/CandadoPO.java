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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mauricio.camara
 */
@Scope("singleton")
@Repository
public class CandadoPO
{
    private static final Logger logger = LogManager.getLogger(CandadoPO.class );
    
    @Autowired
    private RecobMap mapper;
    
    public CandadoTO guardar(CandadoTO candado)
    {
        logger.info ("guardar[INI] candado: {}", candado);
        
        if( candado.getId() == null )
        {
            mapper.insertCandado( candado );
            logger.debug("guardar[001] despues de insertar el candado: {}", candado);
        } else
        {
            mapper.updateCandado( candado );
            logger.debug("guardar[002] despues de actualizar la candado: {}", candado);
        }
        
        logger.info ("guardar[FIN] candado: {}", candado);
        return candado;
    }

    public void eliminar(CandadoTO pkCandado)
    {
        logger.info ("eliminar[INI] candado: {}", pkCandado );
        mapper.deleteCandado(pkCandado);
        logger.info ("eliminar[FIN] despues de eliminar a la candado: {}", pkCandado );
    }

    public CandadoTO get(CandadoTO pkCandado )
    {
        logger.info ("get[INI] pkCandado: {}", pkCandado );
        
        Map<String, Object> params = new HashMap<>();
        params.put( "ubicacion", pkCandado.getUbicacion() );
        params.put( "candado", pkCandado );
        logger.debug ("get[001] parametros: {}", params);
        
        List<CandadoTO> candados = mapper.selectCandados( params );
        logger.debug ("get[002] despues de ejecutar el select: {}", candados.size() );
        
        if(candados.isEmpty())
        {
            logger.info ("get[FIN] no se encontró registro del candado: {}", params );
            return null;
        }
        
        logger.info ("get[FIN] candado encontrado: {}", candados.get(0) );
        return candados.get(0);
    }
    
    public List<CandadoTO> getList(UbicacionTO pkUbicacion, Boolean vigencia)
    {
        logger.info ("get[INI] pkUbicacion: {}", pkUbicacion);
        logger.info ("get[INI] vigencia: {}", vigencia);
        
        Map<String, Object> parms = new HashMap<>();
        parms.put("ubicacion", pkUbicacion);
        parms.put("vigencia", vigencia);
        logger.debug("get[001] parametros: {}", parms);
        
        List<CandadoTO> candados = mapper.selectCandados( parms );
        logger.debug("get[002] despues de ejecutar el select: {}", candados.size() );

        logger.info ("get[FIN] candados: {}", candados );
        return candados;
    }
    
    public List<CandadoTO> getList(UbicacionTO pkUbicacion, PersonaTO pkPersona, Boolean vigencia)
    {
        logger.info ("getList[INI] pkUbicacion: {}", pkUbicacion);
        logger.info ("getList[INI] pkPersona: {}", pkPersona);
        
        Map<String, Object> parms = new HashMap<>();
        parms.put("ubicacion", pkUbicacion);
        parms.put("persona", pkPersona);
        parms.put("vigencia", vigencia);
        logger.debug("getList[001] parametros: {}", parms);

        List<CandadoTO> candados = mapper.selectCandados( parms );
        logger.debug("getList[002] despues de ejecutar el select: {}", candados.size() );
        
        logger.info ("getList[FIN] candado encontrado: {}", candados.size() );
        return candados;
    }
}
