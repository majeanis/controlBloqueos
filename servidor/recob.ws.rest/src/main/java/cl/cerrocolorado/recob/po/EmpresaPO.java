package cl.cerrocolorado.recob.po;

import cl.cerrocolorado.recob.po.map.RecobMap;
import cl.cerrocolorado.recob.to.EmpresaTO;
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
public class EmpresaPO
{
    private static final Logger logger = LogManager.getLogger(EmpresaPO.class );
    
    @Autowired
    private RecobMap mapper;
    
    public EmpresaTO guardar(EmpresaTO empresa)
    {
        logger.info ("guardar[INI] empresa: {}", empresa);
        
        if( empresa.getId() == null )
        {
            mapper.insertEmpresa( empresa );
            logger.debug("guardar[001] despues de insertar el registro: {}", empresa);
        } else
        {
            mapper.updateEmpresa( empresa );
            logger.debug("guardar[002] despues de actualizar el registro: {}", empresa);
        }
        
        logger.info ("guardar[FIN] registro guardado con exito: {}", empresa);
        return empresa;
    }

    public void eliminar(EmpresaTO pkEmpresa)
    {
        logger.info ("eliminar[INI] pkEmpresa: {}", pkEmpresa );
        mapper.deleteEmpresa(pkEmpresa);
        logger.info ("eliminar[FIN] despues de eliminar el registro: {}", pkEmpresa );
    }

    public EmpresaTO get(EmpresaTO pkEmpresa )
    {
        logger.info ("get[INI] pkEmpresa: {}", pkEmpresa );
        
        Map<String, Object> params = new HashMap<>();
        params.put( "empresa", pkEmpresa );
        logger.debug ("get[001] parametros: {}", params);
        
        List<EmpresaTO> lista = mapper.selectEmpresas( params );
        logger.debug ("get[002] despues de ejecutar el select: {}", lista.size() );
        
        if(lista.isEmpty())
        {
            logger.info ("get[FIN] no se encontró registro del candado: {}", params );
            return null;
        }
        
        logger.info ("get[FIN] registro encontrado: {}", lista.get(0) );
        return lista.get(0);
    }
    
    public List<EmpresaTO> get(Boolean vigencia)
    {
        logger.info ("getVigentes[INI] vigencia: {}", vigencia);
        
        Map<String, Object> parms = new HashMap<>();
        parms.put("vigencia", vigencia);
        logger.debug("getVigentes[001] parametros: {}", parms );
        
        List<EmpresaTO> lista = mapper.selectEmpresas( parms );
        logger.debug("getVigentes[002] despues de ejecutar el select: {}", lista.size() );

        logger.info ("getVigentes[FIN] registros: {}", lista.size() );
        return lista;
    }
}
