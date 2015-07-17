package cl.cerrocolorado.recob.po;

import cl.cerrocolorado.recob.po.map.RecobMap;
import cl.cerrocolorado.recob.to.EmpresaTO;
import cl.cerrocolorado.recob.to.PersonaTO;
import cl.cerrocolorado.recob.to.TrabajadorTO;
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
public class TrabajadorPO implements BasePO<TrabajadorTO>
{
    private static final Logger logger = LogManager.getLogger(TrabajadorPO.class );
    
    @Autowired
    private RecobMap mapper;
    
    @Override
    public TrabajadorTO guardar(TrabajadorTO trabajador)
    {
        logger.info ("guardar[INI] trabajador: {}", trabajador);

        // primero validamos si existe el registro de la persona
        PersonaTO persona = getPersona(trabajador);
        if( persona == null )
        {
            mapper.insertPersona(trabajador);
            logger.debug("guardar[001] despues de crear a la persona: {}", trabajador);

            mapper.insertTrabajador(trabajador);
            logger.debug("guardar[002] despues de crear al trabajador: {}", trabajador);
        
            logger.info ("guardar[FIN] registro creado con éxito: {}", trabajador);
            return trabajador;
        }
        
        // En este punto ya existe el registro de la Persona
        trabajador.setId(persona.getId());
        mapper.updatePersona(trabajador);
        logger.debug("guardar[003] despues de actualizar a la persona: {}", trabajador);
        
        // Validamos si ya existe la relación entre Trabajador y Empresa
        TrabajadorTO otro = get(trabajador);
        if(otro==null)
        {
            mapper.insertTrabajador(trabajador);
            logger.info("guardar[004] después de insertar al trabajador: {}", trabajador);
        } else
        {
            mapper.updateTrabajador(trabajador);
            logger.info("guardar[005] después de actualizar al trabajador: {}", trabajador);            
        }

        logger.info ("guardar[FIN] registro guardado con éxito: {}", trabajador);
        return trabajador;
    }

    @Override
    public void eliminar(TrabajadorTO pkTrabajador)
    {
        logger.info ("eliminar[INI] trabajador: {}", pkTrabajador );
        mapper.deleteTrabajador(pkTrabajador);
        logger.info ("eliminar[FIN] despues de eliminar el registro: {}", pkTrabajador );
    }

    @Override
    public TrabajadorTO get(TrabajadorTO pkTrabajador )
    {
        logger.info ("get[INI] pkTrabajador: {}", pkTrabajador );

        Map<String, Object> params = new HashMap<>();
        params.put( "trabajador", pkTrabajador );
        params.put( "empresa", pkTrabajador.getEmpresa() );
        logger.debug ("get[001] parametros: {}", params);
        
        List<TrabajadorTO> lista = mapper.selectTrabajadores( params );
        logger.debug ("get[002] despues de ejecutar el select: {}", lista.size() );
        
        if(lista.isEmpty())
        {
            logger.info ("get[FIN] no se encontró registro: {}", params );
            return null;
        }
        
        logger.info ("get[FIN] registo encontrado: {}", lista.get(0) );
        return lista.get(0);
    }
    
    public List<TrabajadorTO> getList(EmpresaTO pkEmpresa, Boolean vigencia)
    {
        logger.info ("getList[INI] pkEmpresa: {}", pkEmpresa);
        logger.info ("getList[INI] vigencia: {}", vigencia);

        Map<String, Object> parms = new HashMap<>();
        parms.put("empresa", pkEmpresa);
        parms.put("vigencia", vigencia);
        logger.debug("getList[001] parametros: {}", parms);
        
        List<TrabajadorTO> lista = mapper.selectTrabajadores( parms );
        logger.debug("getList[002] despues de ejecutar el select: {}", lista.size() );

        logger.info ("getList[FIN] cantidad de registros encontrados: {}", lista.size() );
        return lista;
    }

    public List<TrabajadorTO> getList(Boolean vigencia)
    {
        logger.info ("getList[INI] vigencia: {}", vigencia);

        Map<String, Object> parms = new HashMap<>();
        parms.put("vigencia", vigencia);
        logger.debug("getList[001] parametros: {}", parms);
        
        List<TrabajadorTO> lista = mapper.selectTrabajadores( parms );
        logger.debug("getList[002] despues de ejecutar el select: {}", lista.size() );

        logger.info ("getList[FIN] cantidad de registros encontrados: {}", lista.size() );
        return lista;
    }

    public PersonaTO getPersona(PersonaTO pkPersona)
    {
        logger.info ("getPersona[INI] pkPersona: {}", pkPersona );
        
        Map<String, Object> parms = new HashMap<>();
        parms.put( "persona", pkPersona);
        logger.debug("getPersona[001] parametros: {}", parms );
        
        List<PersonaTO> lista = mapper.selectPersonas(parms);
        logger.debug("getPersona[002] despues de ejecutar el select: {}", lista.size() );
        
        if( lista.isEmpty() )
        {
            logger.info ("getPersona[FIN] no se encontró registro: {}", pkPersona );
            return null;
        }
        
        logger.info ("getPersona[FIN] registro encontrado: {}", lista.get(0));
        return lista.get(0);
    }
    
    public TrabajadorTO getVigente(TrabajadorTO pkTrabajador)
    {
        logger.info("getVigente[INI] pkTrabajador: {}", pkTrabajador);

        Map<String,Object> parms = new HashMap<>();
        parms.put("trabajador", pkTrabajador);
        parms.put("vigencia", true);
        logger.debug("getVigente[001] parámetros para la query: {}", parms);
        
        List<TrabajadorTO> lista = mapper.selectTrabajadores(parms);
        if(lista.isEmpty())
        {
            logger.info( "getVigente[FIN] no se encontró registro");
            return null;
        }
        
        logger.info( "getVigente[FIN] registro encontrado: {}", lista.get(0) );
        return lista.get(0);
    }

    @Override
    public boolean esEliminable(TrabajadorTO pk)
    {
        logger.info ("esEliminable[INI] pkTrabajador: {}", pk);
        
        int relaciones = mapper.childsTrabajador(pk);
        
        logger.info ("esEliminable[FIN] relaciones: {}", relaciones);
        return relaciones > 0;
    }
}
