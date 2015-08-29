package cl.cerrocolorado.recob.bo.beans;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cl.cerrocolorado.recob.bo.EquipoBO;
import cl.cerrocolorado.recob.po.EquipoPO;
import cl.cerrocolorado.recob.to.EquipoTO;
import cl.cerrocolorado.recob.to.EquipoTagsTO;
import cl.cerrocolorado.recob.to.TagTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ResultadoProceso;
import cl.cerrocolorado.recob.utils.Transaccional;
import cl.cerrocolorado.recob.utils.mensajes.RegistrosQueryInfo;
import java.util.Optional;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author mauricio.camara
 */
@Scope("singleton")
@Service("equipoBO")
public class EquipoBean implements EquipoBO
{
    private static final Logger logger = LogManager.getLogger(EquipoBean.class);

    @Autowired
    private EquipoPO equipoPO;

    private Respuesta<EquipoTO> guardar(EquipoTO equipo, boolean esNuevo)
    {
        logger.info("guardar[INI] equipo: {}", equipo);
        logger.info("guardar[INI] esNuevo: {}", esNuevo);

        Resultado rtdo = new ResultadoProceso();
        if (equipo == null)
        {
            rtdo.addError(this.getClass(), "Debe informar los datos del Equipo");
            logger.info("guardar[FIN] se informó el objeto en NULL");
            return Respuesta.of(rtdo);
        }
        if (equipo.getUbicacion() == null || equipo.getUbicacion().isKeyBlank())
        {
            rtdo.addError(this.getClass(), "El equipo debe estar asociado a una ubicación");
        }
        if (equipo.getVigente() == null)
        {
            rtdo.addError(this.getClass(), "Debe informar la vigencia del Equipo");
        }
        if (StringUtils.isBlank(equipo.getCodigo()))
        {
            rtdo.addError(this.getClass(), "Debe informar el código del equipo");
        }
        if(!rtdo.esExitoso())
        {
            logger.info("guardar[FIN] se detectaron errores de validación");
            return Respuesta.of(rtdo);
        }
        
        EquipoTO otro = equipoPO.obtener(equipo);
        logger.debug("guardar[001] después de buscar otro equipo: {}", otro);
        if(esNuevo)
        {
            if (otro != null)
            {
                rtdo.addError(this.getClass(), "Ya existe equipo con código %s", equipo.getCodigo());
            }
        } 
        else if ( otro == null )
        {
            rtdo.addError(this.getClass(), "No existe equipo con código %s", equipo.getCodigo());
        } 
        else
        {
            equipo.setId(otro.getId());
        }

        if(!rtdo.esExitoso())
        {
            logger.info("guardar[FIN] se detectaron errores de validación");
            return Respuesta.of(rtdo);
        }
        
        equipoPO.guardar(equipo);
        logger.debug("guardar[002] después de guardar el equipo: {}", equipo);
        
        rtdo.addMensaje(this.getClass(), "Equipo guardado con éxito");
        
        logger.info ("guardar[FIN] equipo guardado con éxito: {}", equipo);
        return Respuesta.of(rtdo,equipo);
    }

    @Override
    @Transaccional
    public Respuesta<EquipoTO> crear(EquipoTO datos) throws Exception
    {
        return guardar(datos, true);
    }

    @Override
    @Transaccional
    public Respuesta<EquipoTO> modificar(EquipoTO datos) throws Exception
    {
        return guardar(datos, false);
    }
    
    @Transaccional
    @Override
    public Respuesta<EquipoTO> eliminar(EquipoTO pk) throws Exception
    {
        logger.info ("eliminar[INI] pk: {}", pk);
        
        Resultado rtdo = new ResultadoProceso();
        
        if(pk==null || pk.isKeyBlank() )
        {
            rtdo.addError(this.getClass(), "Debe informar identificación del equipo");
            logger.info("eliminar[FIN] no se informo la pk del equipo: {}", pk);
            return Respuesta.of(rtdo);
        }
        
        EquipoTagsTO equipo = equipoPO.obtener(pk);
        if(equipo == null)
        {
            rtdo.addError(this.getClass(), "No existe equipo con código %s", pk.getCodigo());
            logger.info("eliminar[FIN] no se encontró registro del equipo: {}", pk);
            return Respuesta.of(rtdo);
        }
    
        // Revisamos si es posible eliminar los TAGs
        for( TagTO tag: equipo.getTags())
        {
            if( !equipoPO.esTagEliminable(tag) )
            {
                rtdo.addError(this.getClass(), "TAG %s tiene registros asociados", tag.getCodigo());
            }
        }

        if(!rtdo.esExitoso())
        {
            logger.info("eliminar[FIN] se encontraron errores de validación");
            return Respuesta.of(rtdo);
        }
        
        equipoPO.eliminarTags(equipo);
        equipoPO.eliminar(equipo);
        
        rtdo.addMensaje(this.getClass(), "Registro eliminado con éxito");

        logger.info ("eliminar[FIN] registro eliminado con éxito: {}", equipo);
        return Respuesta.of(rtdo,equipo);
    }
    
    private Respuesta<TagTO> guardarTag(TagTO tag, boolean esNuevo)
    {
    	logger.info("guardarTag[INI] tag: {}", tag);
    	logger.info("guardarTag[INI] esNuevo: {}", esNuevo);
        
    	Resultado rtdo = new ResultadoProceso();
    	if( tag == null )
    	{
    		rtdo.addError(this.getClass(), "Debe informar los datos del TAG");
    		logger.info("guardarTag[FIN] el tag llegó en NULL");
    		return Respuesta.of(rtdo);
    	}
    	
    	if(tag.getEnergiaCero() == null)
    	{
    		rtdo.addError(this.getClass(), "Debe informar la energía cero del TAG" );
    	}
    	if(tag.getVigente() == null)
    	{
    		rtdo.addError(this.getClass(), "Debe informar la vigencia del TAG" );
    	}
    	if(StringUtils.isBlank(tag.getDescripcion()))
    	{
    		rtdo.addError(this.getClass(), "Debe informar la descripción del TAG" );
		}
    	if(tag.getEquipo()==null || tag.getEquipo().isKeyBlank())
    	{
    		rtdo.addError(this.getClass(), "El TAG debe estar asociado a un equipo" );
    	}
    	if(StringUtils.isBlank(tag.getCodigo()))
    	{
    		rtdo.addError(this.getClass(),  "Debe informar el código del TAG" );
    	}
        
        TagTO otro = equipoPO.getTag(tag);
    	if(esNuevo)
        {
            if( otro != null )
            {
                rtdo.addError(this.getClass(), "Ya existe TAG con código %s", tag.getCodigo());
            }
        } 
        else if( otro == null )
        {
            rtdo.addError(this.getClass(), "No existe TAG con código %s", tag.getCodigo());
        } 
        else
        {
            tag.setId(otro.getId());
        }

    	if(!rtdo.esExitoso())
    	{
    		logger.info("guardarTag[FIN] hubo errores de validación: {}", rtdo);
    		return Respuesta.of(rtdo);
    	}

    	equipoPO.guardarTag(tag);
        logger.debug("guardar[001] después de guardar el TAG: {}", tag);

        rtdo.addMensaje(this.getClass(), "TAG guardado con éxito");
        
    	logger.info("guardarTag[FIN] tag guardado con éxito: {}", tag);
    	return Respuesta.of(rtdo, tag);
    }

    @Override
    @Transaccional
    public Respuesta<TagTO> crearTag(TagTO tag) throws Exception
    {
        return guardarTag(tag,true);
    }

    @Override
    @Transaccional
    public Respuesta<TagTO> modificarTag(TagTO tag) throws Exception
    {
        return guardarTag(tag,false);
    }

    @Transaccional
    @Override
    public Respuesta<TagTO> eliminarTag(TagTO pk) throws Exception
    {
        logger.info ("eliminarTag[INI] pkTag: {}", pk);
        
        Resultado rtdo = new ResultadoProceso();
        if(pk==null || pk.isKeyBlank())
        {
            rtdo.addError(this.getClass(), "Debe informar identificación del TAG");
            logger.info ("eliminarTag[FIN] no se informó completa la pk: {}", pk);
            return Respuesta.of(rtdo);
        }
        
        TagTO tag = equipoPO.getTag(pk);
        if( tag == null)
        {
            rtdo.addError(this.getClass(), "No existe TAG con N° %s", pk.getCodigo() );
            logger.info("eliminarTag[FIN] no existe el TAG: {}", pk);
            return Respuesta.of(rtdo);
        }
        
        if( equipoPO.esTagEliminable(tag))
        {
            rtdo.addError(this.getClass(), "TAG tiene registros asociados");
            logger.info("eliminarTag[FIN] tag no es eliminable: {}", tag );
            return Respuesta.of(rtdo);
        }

        equipoPO.eliminarTag(tag);
        rtdo.addMensaje(this.getClass(), "Registro eliminado con éxito");
        
        logger.info ("eliminarTag[FIN] registro eliminado con éxito: {}", tag);        
        return Respuesta.of(rtdo,tag);
    }
    
    @Override
    public Respuesta<EquipoTO> get(EquipoTO pk)
    {
        Respuesta<EquipoTagsTO> r = getEquipo( pk );
        EquipoTO equipo = r.getContenido().orElse(null);
        return Respuesta.of(r.getResultado(), equipo);
    }

    @Override
    public Respuesta<EquipoTagsTO> getEquipo(EquipoTO pk)
    {
        logger.info("getEquipo[INI] pk: {}", pk);

        Resultado rtdo = new ResultadoProceso();

        if(pk==null || pk.isKeyBlank() )
        {
            rtdo.addError(this.getClass(), "Debe informar la identificación del Equipo");
            return Respuesta.of(rtdo);
        }
        
        EquipoTagsTO equipo = equipoPO.obtener(pk);
        if( equipo == null )
        {
            rtdo.addError(this.getClass(), "No existe equipo con código %s", pk.getCodigo());
        }

        logger.info("getEquipo[FIN] registro encontrado: {}", equipo);
        return Respuesta.of(rtdo, equipo);
    }

    @Override
    public Respuesta<List<EquipoTO>> getEquipos(UbicacionTO pkUbicacion, 
                                                Optional<Boolean> vigencia)
    {
        logger.info("getEquipos[INI] pkUbicacion: {}", pkUbicacion);
        logger.info("getEquipos[INI] vigencia: {}", vigencia);

        Resultado rtdo = new ResultadoProceso();
        if(pkUbicacion == null || pkUbicacion.isKeyBlank())
        {
            rtdo.addMensaje(this.getClass(), "Debe informar la ubicación" );
            logger.info("getEquipos[FIN] no se informo la ubicación");
            return Respuesta.of(rtdo);
        }
        
        List<EquipoTO> lista = equipoPO.getList(pkUbicacion, vigencia);
        logger.debug("getEquipos[001] después de buscar los datos: {}", lista.size() );

        rtdo.addMensaje(new RegistrosQueryInfo(this.getClass(), lista.size()));

        logger.info("getEquipos[FIN] cantidad de equipos encontrados: {}", lista.size() );
        return Respuesta.of(rtdo, lista);
    }
    
    @Override
    public Respuesta<TagTO> getTag(TagTO pk)
    {
        logger.info ("getTag[INI] pkTag: {}", pk );

        Resultado rtdo = new ResultadoProceso();
        if( pk == null || pk.isKeyBlank() )
        {
            rtdo.addError(this.getClass(), "Debe informar la identificación del TAG");
            logger.info ("getTag[FIN] no se informó completa la key: {}", pk);
            return Respuesta.of(rtdo);
        }
        
        TagTO tag = equipoPO.getTag(pk);
        if( tag == null )
        {
            rtdo.addError(this.getClass(), "No existe TAG %s", pk.getCodigo());
        }
        
        logger.info ("getTag[FIN] tag retornado: {}", tag);
        return Respuesta.of(rtdo, tag);
    }
    
    @Override
    public Respuesta<List<TagTO>> getTagsEnergiaCero(EquipoTO pk)
    {
        logger.info("getTagsEnergiaCero[INI] pk: {}", pk);
        
        Resultado rtdo = new ResultadoProceso();
        if(pk==null || pk.isKeyBlank() )
        {
            rtdo.addError(this.getClass(), "Debe informar el equipo" );
            logger.info("getTagsEnergiaCero[FIN] no se informó bien la pk del equipo: {}", pk);
            return Respuesta.of(rtdo);
        }
        
        List<TagTO> lista = equipoPO.getTags(pk, Optional.of(Boolean.TRUE), Optional.of(Boolean.TRUE));
        rtdo.addMensaje(new RegistrosQueryInfo(this.getClass(), lista.size()));

        logger.info("getTagsEnergiaCero[FIN] registros retornados: {}", lista );
        return Respuesta.of(rtdo,lista);
    }

    @Override
    public Respuesta<List<TagTO>> getTags(EquipoTO pk, Optional<Boolean> vigencia)
    {
        logger.info("getTags[INI] pk: {}", pk);
        logger.info("getTags[INI] vigencia: {}", vigencia);
        
        Resultado rtdo = new ResultadoProceso();
        if(pk==null || pk.isKeyBlank() )
        {
            rtdo.addError(this.getClass(), "Debe informar la ubicación" );
            logger.info("getTags[FIN] no se informó bien la pk del equipo: {}", pk);
            return Respuesta.of(rtdo);
        }
        
        List<TagTO> lista = equipoPO.getTags(pk, Optional.empty(), vigencia);
        rtdo.addMensaje(new RegistrosQueryInfo(this.getClass(), lista.size()));

        logger.info("getTags[FIN] registros retornados: {}", lista.size());
        return Respuesta.of(rtdo, lista);
    }
}
