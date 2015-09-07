package cl.cerrocolorado.recob.po;

import cl.cerrocolorado.recob.po.map.RecobMap;
import cl.cerrocolorado.recob.to.entidades.ResponsableTO;
import cl.cerrocolorado.recob.to.entidades.UbicacionTO;
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
public class ResponsablePO implements BasePO<ResponsableTO>
{
    private static final Logger logger = LogManager.getLogger(ResponsablePO.class);
    
    @Autowired
    private RecobMap mapper;


    @Override
    public ResponsableTO save(ResponsableTO datos)
    {
        logger.info ("save[INI] datos: {}", datos);
        
        if(datos.isIdBlank())
        {
            mapper.insertResponsable(datos);
            logger.debug("save[001] después de insertar al responsable: {}", datos);            
        } else
        {
            mapper.updateResponsable(datos);
            logger.debug("save[002] después de actualizar al responsable: {}", datos);
        }
        
        logger.info ("save[FIN] responsable guardado con éxito: {}", datos);
        return datos;
    }

    @Override
    public void delete(ResponsableTO pk)
    {
        logger.info ("delete[INI] pk: {}", pk);
        mapper.deleteResponsable(pk);
        logger.info ("delete[FIN] registro eliminado con éxito: {}", pk );
    }

    @Override
    public boolean isDeleteable(ResponsableTO pk)
    {
        logger.info ("isDeleteable[INI] pk: {}", pk);
        long i = mapper.childsResponsable(pk);
        logger.info ("isDeleteable[FIN] registros hijos: {}", i);
        return i == 0;
    }

    private ResponsableTO get(Map<String,Object> parms)
    {
        logger.info ("get[INI] parms: {}", parms);
        
        List<ResponsableTO> lista = mapper.selectResponsables(parms);
        logger.debug("get[001] después de hacer el select: {}", lista.size());

        if(lista.isEmpty())
        {
            logger.info("get[FIN] no se encontró registro");
            return null;
        }
        
        logger.info("get[FIN] registro encontrado: {}", lista.get(0));
        return lista.get(0);
    }

    @Override
    public ResponsableTO get(ResponsableTO pk)
    {
        logger.info ("get[INI] pk: {}", pk);
        
        Map<String,Object> parms = new HashMap<>();
        parms.put("ubicacion", pk.getUbicacion());
        parms.put("persona", pk.getPersona());
        parms.put("empresa", pk.getEmpresa());
        parms.put("fechaIngreso", pk.getFechaIngreso());
        logger.debug("get[001] parametros de búsqueda: {}", parms);
        
        ResponsableTO r = get(parms);
        
        logger.info("get[FIN] registro encontrado: {}", r);
        return r;
    }

    @Override
    public ResponsableTO getById(ResponsableTO id)
    {
        logger.info ("get[INI] id: {}", id);
        
        Map<String,Object> parms = new HashMap<>();
        parms.put("ubicacion", id.getUbicacion());
        parms.put("id", id.getId());
        
        ResponsableTO r = get(parms);
        
        logger.info("get[FIN] registro encontrado: {}", r);
        return r;
    }
    
    public ResponsableTO getVigente(UbicacionTO pk)
    {
        logger.info ("getVigente[INI] pkResponsable: {}", pk);
        
        Map<String,Object> parms = new HashMap<>();
        parms.put("ubicacion", pk);
        parms.put("vigente", true);
        logger.debug("getVigente[001] parametros de búsqueda: {}", parms);
        
        List<ResponsableTO> lista = mapper.selectResponsables(parms);
        logger.debug("getVigente[002] después de hacer el select: {}", lista.size());

        if(lista.isEmpty())
        {
            logger.info("getVigente[FIN] no se encontró registro");
            return null;
        }
        
        logger.info("getVigente[FIN] registro encontrado: {}", lista.get(0));
        return lista.get(0);
    }
}
 