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
        parms.put("equipo", pk);
        logger.debug("get[001] parámetros del select: {}", parms);
        
        List<EquipoTO> lista = mapper.selectEquipos(parms);
        logger.debug("get[001] despues de ejecutare el select: {}", lista.size() );
        
        if(lista.isEmpty())
        {
            logger.info("get[FIN] no se obtuvieron registros" );
            return null;
        }
        
        List<TagTO> tags = mapper.selectTags(parms);
        logger.debug("get[003] despues de buscar los tags: size {}", tags.size());
        lista.get(0).setTags(tags);
        
        logger.info("get[FIN] registro encontrado: {}", lista.get(0));
        return lista.get(0);
    }

    @Override
    public EquipoTO guardar(EquipoTO datos)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean esEliminable(EquipoTO pk)
    {
        logger.info ("esEliminable[INI] pkEquipo: {}", pk);
        
        int relaciones = mapper.childsEquipo(pk);
        
        logger.info ("esEliminable[FIN] relaciones: {}", relaciones);
        return relaciones > 0;
    }
}
