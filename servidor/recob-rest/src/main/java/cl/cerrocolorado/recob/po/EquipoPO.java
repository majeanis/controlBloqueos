package cl.cerrocolorado.recob.po;

import cl.cerrocolorado.recob.po.map.RecobMap;
import cl.cerrocolorado.recob.to.EquipoTO;
import cl.cerrocolorado.recob.to.EquipoTagsTO;
import cl.cerrocolorado.recob.to.TagTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author mauricio.camara
 */
public class EquipoPO implements BasePO<EquipoTO>
{
    private static final Logger logger = LogManager.getLogger(EquipoPO.class);
    
    @Autowired
    private RecobMap mapper;

    @Override
    public void eliminar(EquipoTO pkEquipo)
    {
        logger.info ("eliminar[INI] pkEquipo: {}", pkEquipo);
        
        mapper.deleteEquipo(pkEquipo);
        
        logger.info ("eliminar[FIN] despues de eliminar el registro: {}", pkEquipo);
    }

    @Override
    public EquipoTagsTO get(EquipoTO pk)
    {
        logger.info("get[INI] pk: {}", pk);

        Map<String,Object> parms = new HashMap<>();
        parms.put("ubicacion", pk.getUbicacion());
        parms.put("equipo"   , pk);
        logger.debug("get[001] parámetros del select: {}", parms);
        
        List<EquipoTO> lista = mapper.selectEquipos(parms);
        logger.debug("get[001] después de ejecutare el select: {}", lista.size() );
        
        if(lista.isEmpty())
        {
            logger.info("get[FIN] no se obtuvieron registros" );
            return null;
        }

        EquipoTagsTO equipo = new EquipoTagsTO();
        BeanUtils.copyProperties(lista.get(0), equipo);
        logger.debug("get[001] después copiar el objeto a EquipoTagsTO: {}", equipo);
        
        List<TagTO> tags = getTags(equipo,null,null);
        logger.debug("get[002] después de buscar los tags: size {} {}", tags.size(), equipo);
        
        equipo.setTags(tags);
        logger.info("get[FIN] registro encontrado: {}", equipo);
        return equipo;
    }

    @Override
    public EquipoTO guardar(EquipoTO equipo)
    {
        logger.info ("guardar[INI] equipo: {}", equipo);

        if( equipo.isIdBlank() )
        {
            mapper.insertEquipo(equipo);
            logger.info("guardar[FIN] registro creado con éxito: {}", equipo);
        } else
        {
            mapper.updateEquipo(equipo);
            logger.debug("guardar[FIN] registro actualizado con éxito: {}", equipo);
        }

        return equipo;
    }

    @Override
    public boolean esEliminable(EquipoTO pk)
    {
        logger.info ("esEliminable[INI] pkEquipo: {}", pk);
        
        int relaciones = mapper.childsEquipo(pk);
        
        logger.info ("esEliminable[FIN] relaciones: {}", relaciones);
        return relaciones == 0;
    }

    public boolean esTagEliminable(TagTO pk)
    {
        logger.info ("esTagEliminable[INI] pk: {}", pk);
        
        int relaciones = mapper.childsTag(pk);
        
        logger.info ("esTagEliminable[FIN] relaciones: {}", relaciones);
        return relaciones > 0;
    }

    public TagTO getTag(TagTO pk)
    {
        logger.info("getTag[INI] tag: {}", pk);
        
        Map<String,Object> parms = new HashMap<>();
        parms.put("ubicacion"  , pk.getEquipo().getUbicacion() );
        parms.put("equipo"     , pk.getEquipo() );
        parms.put("tag"        , pk);
        parms.put("energiaCero", null);
        parms.put("vigencia"   , null);
        logger.info("getTag[001] parámetros de la consulta: {}", parms);
        
        List<TagTO> lista = mapper.selectTags(parms);
        logger.info("getTag[002] después de ejecutado el select: {}", lista.size());
        
        if( lista.isEmpty() )
        {
            logger.info("getTag[FIN] no se encontró el tag: {}", pk);
            return null;
        }
        
        logger.info("getTag[FIN] registro encontrado: {}", lista.get(0));
        return lista.get(0);
    }
    
    public List<TagTO> getTags(EquipoTO pk, 
                               Optional<Boolean> energiaCero, 
                               Optional<Boolean> vigencia)
    {
        logger.info("getTags[INI] pk: {}", pk);

        Map<String,Object> parms = new HashMap<>();
        parms.put("ubicacion"  , pk.getUbicacion());
        parms.put("equipo"     , pk);
        parms.put("energiaCero", energiaCero.orElse(null));
        parms.put("vigencia"   , vigencia.orElse(null));
        logger.debug("getTags[001] parámetros del select: {}", parms);
        
        List<TagTO> tags = mapper.selectTags(parms);
        logger.debug("getTags[002] después de buscar los tags: size {}", tags.size());

        logger.info("getTags[FIN] registros encontrados: {}", tags);
        return tags;
    }
    
    public TagTO guardarTag(TagTO tag)
    {
        logger.info("guardarTag[INI] tag: {}", tag);
        
        if(tag.isIdBlank())
        {
            mapper.insertTag(tag);
            logger.debug("guardarTag[001] después de insertar el tag: {}", tag);
        } else
        {
            mapper.updateTag(tag);
            logger.debug("guardarTag[001] después de actualizar el tag: {}", tag);            
        }

        logger.info("guardarTag[FIN] tag guardado con éxito: {}", tag);
        return tag;
    }

    public void eliminarTag(TagTO pk)
    {
        logger.info ("eliminarTag[INI] tag: {}", pk);
        mapper.deleteTag(pk);
        logger.info ("eliminarTag[FIN] tag eliminado con éxito: {}", pk);
    }

    public void eliminarTags(EquipoTO pk)
    {
        logger.info ("eliminarTags[INI] tag: {}", pk);
        mapper.deleteTags(pk);
        logger.info ("eliminarTags[FIN] tags eliminados con éxito: {}", pk);
    }

    public List<EquipoTO> getList(UbicacionTO pkUbicacion, 
                                  Optional<Boolean> vigencia)
    {
        logger.info ("getList[INI] vigencia: {}", vigencia);
        
        Map<String, Object> parms = new HashMap<>();
        parms.put("ubicacion", pkUbicacion);
        parms.put("vigencia", vigencia.orElse(null));
        logger.debug("getList[001] parametros: {}", parms );
        
        List<EquipoTO> lista = mapper.selectEquipos(parms);
        logger.debug("getList[002] despues de ejecutar el select: {}", lista.size() );

        logger.info ("getVigentes[FIN] registros: {}", lista.size() );
        return lista;
    }
}
