package cl.cerrocolorado.recob.po;

import cl.cerrocolorado.recob.po.map.RecobMap;
import cl.cerrocolorado.recob.to.entidades.EmpresaTO;
import cl.cerrocolorado.recob.to.entidades.PersonaTO;
import cl.cerrocolorado.recob.to.entidades.TrabajadorTO;
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
public class TrabajadorPO implements BasePO<TrabajadorTO>
{
    private static final Logger logger = LogManager.getLogger(TrabajadorPO.class );
    
    @Autowired
    private RecobMap mapper;

    private PersonaTO guardarPersona(PersonaTO persona)
    {
        logger.info ("guardarPersona[INI] persona: {}", persona);
        
        PersonaTO otra = getPersona(persona);
        if( otra == null )
        {
            mapper.insertPersona(persona);
            logger.debug("guardarPersona[001] después de insertar a la persona: {}", persona);
        }
        else
        {
            persona.setId(otra.getId());
            mapper.updatePersona(persona);
            logger.debug("guardarPersona[002] después de actualizar a la persona: {}", persona);
        }
        
        logger.info ("guardarPersona[FIN] persona guardada con éxito: {}", persona);
        return persona;
    }
    
    @Override
    public TrabajadorTO guardar(TrabajadorTO trabajador)
    {
        logger.info ("guardar[INI] trabajador: {}", trabajador);

        PersonaTO persona = guardarPersona(trabajador);
        logger.debug("guardar[001] después de guardar a la persona: {}", persona);

        // Validamos si ya existe la relación entre Persona y Empresa
        trabajador.setId(persona.getId());
        TrabajadorTO otro = obtener(trabajador);
        if(otro==null)
        {
            mapper.insertTrabajador(trabajador);
            logger.info("guardar[002] después de insertar al trabajador: {}", trabajador);
        } else
        {
            trabajador.setEmpresa(otro.getEmpresa());
            mapper.updateTrabajador(trabajador);
            logger.info("guardar[003] después de actualizar al trabajador: {}", trabajador);            
        }

        logger.info ("guardar[FIN] trabajador guardado con éxito: {}", trabajador);
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
    public TrabajadorTO obtener(TrabajadorTO pkTrabajador )
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
    
    public List<TrabajadorTO> getList(EmpresaTO pkEmpresa, 
                                      Optional<Boolean> vigencia)
    {
        logger.info ("getList[INI] pkEmpresa: {}", pkEmpresa);
        logger.info ("getList[INI] vigencia: {}", vigencia);

        Map<String, Object> parms = new HashMap<>();
        parms.put("empresa", pkEmpresa);
        parms.put("vigencia", vigencia.orElse(null));
        logger.debug("getList[001] parametros: {}", parms);
        
        List<TrabajadorTO> lista = mapper.selectTrabajadores( parms );
        logger.debug("getList[002] despues de ejecutar el select: {}", lista.size() );

        logger.info ("getList[FIN] cantidad de registros encontrados: {}", lista.size() );
        return lista;
    }

    public List<TrabajadorTO> getList(Optional<Boolean> vigencia)
    {
        logger.info ("getList[INI] vigencia: {}", vigencia);

        Map<String, Object> parms = new HashMap<>();
        parms.put("vigencia", vigencia.orElse(null));
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
        logger.info ("isDeleteable[INI] pkTrabajador: {}", pk);
        int relaciones = mapper.childsTrabajador(pk);
        logger.info ("isDeleteable[FIN] relaciones: {}", relaciones);

        return relaciones == 0;
    }
}
