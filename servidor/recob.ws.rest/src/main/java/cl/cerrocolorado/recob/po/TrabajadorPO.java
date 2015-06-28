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
import org.springframework.stereotype.Repository;

/**
 *
 * @author mauricio.camara
 */
@Repository
public class TrabajadorPO
{
    private static final Logger logger = LogManager.getLogger(TrabajadorPO.class );
    
    @Autowired
    private RecobMap mapper;
    
    public PersonaTO guardar(TrabajadorTO trabajador)
    {
        logger.info ("guardar[INI] trabajador: {}", trabajador);
        
        // Si se necesita aplicar un Update, entonces actualizamos el
        // registro de la Persona, como también el del Trabajador
        if( trabajador.getId() != null )
        {
            mapper.updatePersona( trabajador );
            logger.debug("guardar[001] despues de actualizar a la persona: {}", trabajador);
            
            mapper.updateTrabajador( trabajador );
            logger.debug("guardar[002] despues de actualizar al Trabajador: {}", trabajador);
        
            logger.info ("guardar[FIN] registro guardado con éxito: {}", trabajador);
            return trabajador;
        }
        
        // Si llegamos a este punto, entonces se necesita la creación del Trabajador
        // para lo cual validamos si existe el registro de la Persona.
        Map<String,Object> parms = new HashMap<>();
        parms.put ("persona", trabajador );
        List<PersonaTO> personas = mapper.selectPersonas(parms);
        logger.debug("guardar[003] despues de buscar a la persona: {}", personas);

        // Si no existe registro para la persona, entonces procedemos a crearlo
        // En caso contrario, asignamos su Id al objeto  del  Trabajador,  para
        // garantizar la relación entre ambos registros.
        if( personas.isEmpty() )
        {
            mapper.insertPersona(trabajador);
            logger.debug("guardar[004] despues de crear el registro de la persona: {}", trabajador);
        } else 
        {
            trabajador.setId(personas.get(0).getId());
        }

        // Finalmente insertamos al Trabajador
        mapper.insertTrabajador(trabajador);
        logger.debug("guardar[005] despues de crear el registro del trabajador: {}", trabajador);

        logger.info ("guardar[FIN] registro guardado con exito: {}", trabajador);
        return trabajador;
    }

    public void eliminar(TrabajadorTO pkTrabajador)
    {
        logger.info ("eliminar[INI] trabajador: {}", pkTrabajador );
        mapper.deleteTrabajador(pkTrabajador);
        logger.info ("eliminar[FIN] despues de eliminar el registro: {}", pkTrabajador );
    }

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
    
    public List<TrabajadorTO> get(EmpresaTO pkEmpresa, Boolean vigencia)
    {
        logger.info ("get[INI] pkEmpresa: {}", pkEmpresa);
        logger.info ("get[INI] vigencia: {}", vigencia);

        Map<String, Object> parms = new HashMap<>();
        parms.put("empresa", pkEmpresa);
        parms.put("vigencia", vigencia);
        logger.debug("get[001] parametros: {}", parms);
        
        List<TrabajadorTO> lista = mapper.selectTrabajadores( parms );
        logger.debug("get[002] despues de ejecutar el select: {}", lista.size() );

        logger.info ("get[FIN] cantidad de registros encontrados: {}", lista.size() );
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
}
