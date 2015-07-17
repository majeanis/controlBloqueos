/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.cerrocolorado.recob.po;

import cl.cerrocolorado.recob.po.map.RecobMap;
import cl.cerrocolorado.recob.to.EquipoTO;
import cl.cerrocolorado.recob.to.TagTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    public EquipoTO get(EquipoTO pk)
    {
        logger.info("get[INI] pk: {}", pk);

        Map<String,Object> parms = new HashMap<>();
        parms.put("equipo"   , pk);
        parms.put("ubicacion", pk.getUbicacion());
        logger.debug("get[001] parámetros del select: {}", parms);
        
        List<EquipoTO> lista = mapper.selectEquipos(parms);
        logger.debug("get[001] después de ejecutare el select: {}", lista.size() );
        
        if(lista.isEmpty())
        {
            logger.info("get[FIN] no se obtuvieron registros" );
            return null;
        }

        List<TagTO> tags = getTags(pk,null,null);
        logger.debug("get[002] después de buscar los tags: size {}", tags.size());
        lista.get(0).setTags(tags);

        logger.info("get[FIN] registro encontrado: {}", lista.get(0));
        return lista.get(0);
    }

    @Override
    public EquipoTO guardar(EquipoTO equipo)
    {
        logger.info ("guardar[INI] equipo: {}", equipo);

        // Si se trata de una inserción, entonces creamos el equipo y todos sus tags
        if( equipo.getId() == null )
        {
            mapper.insertEquipo(equipo);
            logger.debug("guardar[001] después de insertar el equipo: {}", equipo);
            
            for( TagTO tag: equipo.getTags())
            {
                tag.setIdEquipo(equipo.getId());
                mapper.insertTag(tag);
                logger.debug("guardar[002] después de insertar el tag: {} {}", tag, equipo);
            }
            
            logger.info("guardar[FIN] registro creado con éxito: {}", equipo);
            return equipo;
        }

        // Si llegamos a este punto, entonces debes aplicar una actualización
        mapper.updateEquipo(equipo);
        logger.debug("guardar[003] después de actualizar el equipo: {}", equipo);

        for( TagTO tag: equipo.getTags())
        {
            if( tag.getId() == null )
            {
                mapper.insertTag(tag);
                logger.debug("guardar[004] después de insertar el tag: {} {}", tag, equipo);
            } else
            {
                mapper.updateTag(tag);
                logger.debug("guardar[005] después de actualizar el tag: {} {}", tag, equipo);                
            }
        }

        // Determinamos los TAGs que no fueron considerados
        // en este proceso para marcarlos como No Vigentes
        List<TagTO> todos = this.getTags(equipo, null, null );
        for( TagTO tag: todos)
        {
            if( !equipo.getTags().contains(tag))
            {
                tag.setVigente(Boolean.FALSE);
                mapper.updateTag(tag);
                logger.debug("guardar[006] después de desactivar tag: {} {}", tag, equipo);                
            }
        }
        
        logger.info ("guardar[FIN] registro actualizado con éxito: {}", equipo);
        return equipo;
    }

    @Override
    public boolean esEliminable(EquipoTO pk)
    {
        logger.info ("esEliminable[INI] pkEquipo: {}", pk);
        
        int relaciones = mapper.childsEquipo(pk);
        
        logger.info ("esEliminable[FIN] relaciones: {}", relaciones);
        return relaciones > 0;
    }
    
    public TagTO getTag(TagTO pk)
    {
        logger.info("getTag[INI] tag: {}", pk);
        
        Map<String,Object> parms = new HashMap<>();
        parms.put("tag", pk);
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
    
    public List<TagTO> getTags(EquipoTO pk, Boolean energiaCero, Boolean vigencia)
    {
        logger.info("get[INI] pk: {}", pk);

        Map<String,Object> parms = new HashMap<>();
        parms.put("equipo"   , pk);
        parms.put("ubicacion", pk.getUbicacion());
        parms.put("energiaCero", energiaCero);
        parms.put("vigencia", vigencia);
        logger.debug("get[001] parámetros del select: {}", parms);
        
        List<TagTO> tags = mapper.selectTags(parms);
        logger.debug("get[002] después de buscar los tags: size {}", tags.size());

        logger.info("get[FIN] registros encontrados: {}", tags);
        return tags;
    }
    
    public TagTO guardarTag(TagTO tag)
    {
        logger.info("guardarTag[INI] tag: {}", tag);
        
        if(tag.getId() == null)
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
}
