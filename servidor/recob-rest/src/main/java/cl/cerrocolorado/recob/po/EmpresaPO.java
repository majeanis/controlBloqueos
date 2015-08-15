package cl.cerrocolorado.recob.po;

import cl.cerrocolorado.recob.po.map.RecobMap;
import cl.cerrocolorado.recob.to.EmpresaTO;
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
public class EmpresaPO implements BasePO<EmpresaTO>
{
    private static final Logger logger = LogManager.getLogger(EmpresaPO.class );
    
    @Autowired
    private RecobMap mapper;
    
    @Override
    public EmpresaTO guardar(EmpresaTO empresa)
    {
        logger.info ("guardar[INI] empresa: {}", empresa);
        if(empresa.isIdBlank())
        {
            mapper.insertEmpresa( empresa );            
            logger.debug("guardar[001] después de insertar la empresa: {}", empresa);
        } else
        {
            mapper.updateEmpresa( empresa );
            logger.debug("guardar[002] después de actualizar la empresa: {}", empresa);
        }

        logger.info ("guardar[FIN] empresa guardad con éxito: {}", empresa);
        return empresa;
    }

    @Override
    public void eliminar(EmpresaTO pkEmpresa)
    {
        logger.info ("delete[INI] pkEmpresa: {}", pkEmpresa );
        mapper.deleteEmpresa(pkEmpresa);
        logger.info ("delete[FIN] despues de eliminar el registro: {}", pkEmpresa );
    }

    @Override
    public boolean esEliminable(EmpresaTO pk)
    {
        logger.info ("isDeleteable[INI] pkEmpresa: {}", pk);
        int relaciones = mapper.childsEmpresa(pk);
        logger.info ("isDeleteable[FIN] relaciones: {}", relaciones);
        return relaciones == 0;
    }
    
    @Override
    public EmpresaTO obtener(EmpresaTO pkEmpresa )
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
    
    public List<EmpresaTO> getList(Optional<Boolean> vigencia)
    {
        logger.info ("getList[INI] vigencia: {}", vigencia);
        
        Map<String, Object> parms = new HashMap<>();
        parms.put("vigencia", vigencia.orElse(null));
        logger.debug("getList[001] parametros: {}", parms );
        
        List<EmpresaTO> lista = mapper.selectEmpresas( parms );
        logger.debug("getList[002] despues de ejecutar el select: {}", lista.size() );

        logger.info ("getVigentes[FIN] registros: {}", lista.size() );
        return lista;
    }
}
