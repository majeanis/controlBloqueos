package cl.cerrocolorado.recob.po;

import cl.cerrocolorado.recob.po.map.RecobMap;
import cl.cerrocolorado.recob.to.ResponsableTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
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
    public ResponsableTO crear(ResponsableTO datos)
    {
        logger.info ("insert[INI] datos: {}", datos);
        mapper.insertResponsable(datos);
        logger.info ("insert[FIN] después de hacer el insert: {}", datos);

        return datos;
    }

    @Override
    public ResponsableTO modificar(ResponsableTO datos)
    {
        logger.info ("update[INI] datos: {}", datos);
        mapper.updateResponsable(datos);
        logger.info ("update[FIN] después de hacer el update: {}", datos);
 
        return datos;
    }

    @Override
    public void eliminar(ResponsableTO pk)
    {
        logger.info ("delete[INI] pk: {}", pk);
        mapper.deleteResponsable(pk);
        logger.info ("delete[FIN] registro eliminado con éxito: {}", pk );
    }

    @Override
    public boolean esEliminable(ResponsableTO pk)
    {
        logger.info ("isDeleteable[INI] pk: {}", pk);
        int i = mapper.childsResponsable(pk);
        logger.info ("isDeleteable[FIN] registros hijos: {}", i);

        return i == 0;
    }

    @Override
    public ResponsableTO obtener(ResponsableTO pk)
    {
        logger.info ("get[INI] pkResponsable: {}", pk);
        
        Map<String,Object> parms = new HashMap<>();
        parms.put("ubicacion", pk.getUbicacion());
        parms.put("persona", pk.getPersona());
        parms.put("empresa", pk.getEmpresa());
        parms.put("fechaIngreso", pk.getFechaIngreso());
        logger.debug("get[001] parametros de búsqueda: {}", parms);
        
        List<ResponsableTO> lista = mapper.selectResponsables(parms);
        logger.debug("get[002] después de hacer el select: {}", lista.size());

        if(lista.isEmpty())
        {
            logger.info("get[FIN] no se encontró registro");
            return null;
        }
        
        logger.info("get[FIN] registro encontrado: {}", lista.get(0));
        return lista.get(0);
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
 