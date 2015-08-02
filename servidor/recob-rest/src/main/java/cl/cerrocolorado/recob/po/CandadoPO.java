package cl.cerrocolorado.recob.po;

import cl.cerrocolorado.recob.po.map.RecobMap;
import cl.cerrocolorado.recob.to.CandadoTO;
import cl.cerrocolorado.recob.to.PersonaTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.to.UsoCandadoTO;
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
public class CandadoPO implements BasePO<CandadoTO>
{
    private static final Logger logger = LogManager.getLogger(CandadoPO.class );
    
    @Autowired
    private RecobMap mapper;
    
    @Override
    public CandadoTO guardar(CandadoTO candado)
    {
        logger.info ("guardar[INI] candado: {}", candado);
        
        if( candado.isIdBlank() )
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

    @Override
    public void eliminar(CandadoTO pkCandado)
    {
        logger.info ("eliminar[INI] candado: {}", pkCandado );
        mapper.deleteCandado(pkCandado);
        logger.info ("eliminar[FIN] despues de eliminar a la candado: {}", pkCandado );
    }

    @Override
    public CandadoTO get(CandadoTO pkCandado )
    {
        logger.info ("get[INI] pkCandado: {}", pkCandado );
        
        Map<String, Object> parms = new HashMap<>();
        parms.put( "ubicacion", pkCandado.getUbicacion() );
        parms.put( "candado", pkCandado );
        logger.debug ("get[001] parametros: {}", parms);
        
        List<CandadoTO> candados = mapper.selectCandados( parms );
        logger.debug ("get[002] despues de ejecutar el select: {}", candados.size() );
        
        if(candados.isEmpty())
        {
            logger.info ("get[FIN] no se encontró registro del candado: {}", parms );
            return null;
        }
        
        logger.info ("get[FIN] candado encontrado: {}", candados.get(0) );
        return candados.get(0);
    }
    
    public CandadoTO getBySerie(CandadoTO pkCandado)
    {
        logger.info ("getBySerie[INI] pkCandado: {}", pkCandado );
        
        Map<String, Object> parms = new HashMap<>();
        parms.put( "candado", new CandadoTO());
        parms.put( "ubicacion", pkCandado.getUbicacion() );
        parms.put( "serie", pkCandado.getSerie() );
        logger.debug ("getBySerie[001] parametros: {}", parms);

        List<CandadoTO> candados = mapper.selectCandados( parms );
        logger.debug ("getBySerie[002] despues de ejecutar el select: {}", candados.size() );
        
        if(candados.isEmpty())
        {
            logger.info ("getBySerie[FIN] no se encontró registro del candado: {}", parms );
            return null;
        }
        
        logger.info ("getBySerie[FIN] candado encontrado: {}", candados.get(0) );
        return candados.get(0);
    }
    
    public List<CandadoTO> getList(UbicacionTO pkUbicacion, Optional<Boolean> vigencia)
    {
        logger.info ("get[INI] pkUbicacion: {}", pkUbicacion);
        logger.info ("get[INI] vigencia: {}", vigencia);
        
        Map<String, Object> parms = new HashMap<>();
        parms.put("ubicacion", pkUbicacion);
        parms.put("vigencia", vigencia.orElse(null));
        logger.debug("get[001] parametros: {}", parms);
        
        List<CandadoTO> candados = mapper.selectCandados( parms );
        logger.debug("get[002] despues de ejecutar el select: {}", candados.size() );

        logger.info ("get[FIN] candados: {}", candados );
        return candados;
    }
    
    public List<CandadoTO> getList(UbicacionTO pkUbicacion, 
                                   Optional<PersonaTO> pkPersona, 
                                   Optional<Boolean> vigencia)
    {
        logger.info ("getList[INI] pkUbicacion: {}", pkUbicacion);
        logger.info ("getList[INI] pkPersona: {}", pkPersona);
        
        Map<String, Object> parms = new HashMap<>();
        parms.put("ubicacion", pkUbicacion);
        parms.put("persona", pkPersona.orElse(null));
        parms.put("vigencia", vigencia.orElse(null));
        logger.debug("getList[001] parametros: {}", parms);

        List<CandadoTO> candados = mapper.selectCandados( parms );
        logger.debug("getList[002] despues de ejecutar el select: {}", candados.size() );
        
        logger.info ("getList[FIN] candado encontrado: {}", candados.size() );
        return candados;
    }

    @Override
    public boolean esEliminable(CandadoTO pk)
    {
        logger.info ("esEliminable[INI] pkCandado: {}", pk);
        
        int relaciones = mapper.childsCandado(pk);
        
        logger.info ("esEliminable[FIN] relaciones: {}", relaciones);
        return relaciones == 0;
    }
    
    public List<UsoCandadoTO> getUsosCandado(Optional<Boolean> vigencia)
    {
        logger.info ("getUsosCandado[INI] vigencia: {}", vigencia);

        Map<String, Object> parms = new HashMap<>();
        parms.put("vigencia", vigencia.orElse(null));
        logger.debug("getUsosCandado[001] parametros: {}", parms);

        List<UsoCandadoTO> lista = mapper.selectUsosCandado( parms );
        logger.debug("getUsosCandados[002] despues de ejecutar el select: {}", lista.size() );
        
        logger.info ("getUsosCandados[FIN] candado encontrado: {}", lista.size() );
        return lista;
    }

    public UsoCandadoTO getUsoCandado(UsoCandadoTO pk)
    {
        logger.info ("getUsoCandado[INI] pk: {}", pk);

        Map<String, Object> parms = new HashMap<>();
        parms.put("uso", pk);
        logger.debug("getUsoCandado[001] parametros: {}", parms);

        List<UsoCandadoTO> lista = mapper.selectUsosCandado( parms );
        logger.debug("getUsoCandado[002] despues de ejecutar el select: {}", lista.size() );
        
        if( lista.isEmpty() )
        {
            logger.info ("getUsoCandado[FIN] no se encontró registro para: {}", pk );
            return null;
        }
        
        logger.info ("getUsoCandado[FIN] registro encontrado: {}", lista.get(0));
        return lista.get(0);
    }
}
