package cl.cerrocolorado.recob.bo.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cl.cerrocolorado.recob.bo.EquipoBO;
import cl.cerrocolorado.recob.po.EquipoPO;
import cl.cerrocolorado.recob.to.EquipoTO;
import cl.cerrocolorado.recob.to.TagTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ResultadoProceso;
import cl.cerrocolorado.recob.utils.Transaccional;

/**
 *
 * @author mauricio.camara
 */
public class EquipoBean implements EquipoBO
{
    private static final Logger logger = LogManager.getLogger(EquipoBean.class);

    @Autowired
    private EquipoPO equipoPO;

    @Transaccional
    @Override
    public Respuesta<EquipoTO> guardar(EquipoTO equipo) throws Exception
    {
       logger.info ("guardar[INI] equipo: {}", equipo);
       
       Resultado rtdo = new ResultadoProceso();
       if(equipo==null)
       {
           rtdo.addError(this.getClass(), "Debe informar los datos del Equipo");
           logger.info ("guardar[FIN] se informó el objeto en NULL");
           return Respuesta.of(rtdo);
       }
       if(equipo.getUbicacion() == null || equipo.getUbicacion().isKeyBlank())
       {
           rtdo.addError(this.getClass(), "El equipo debe estar asociado a una ubicación" );
       }
       if(equipo.getVigente() == null)
       {
           rtdo.addError(this.getClass(), "Debe informar la vigencia del Equipo");
       }
       if(StringUtils.isBlank(equipo.getCodigo()))
       {
           rtdo.addError(this.getClass(),"Debe informar el código del equipo");
       }

       EquipoTO otro = equipoPO.get(equipo);
       logger.debug("guardar[001] después de buscar otro equipo: {}", otro);
       if(otro!=null)
       {
    	   equipo.setId(otro.getId());
       }

       equipoPO.guardar(equipo);
       logger.info ("guardar[FIN] equipo guardado con éxito: {}", equipo);
       return Respuesta.of(equipo);
    }

    @Transaccional
    @Override
    public Resultado eliminar(EquipoTO pk) throws Exception
    {
        logger.info ("eliminar[INI] pk: {}", pk);
        
        Resultado rtdo = new ResultadoProceso();
        
        if(pk==null || pk.isKeyBlank() )
        {
            rtdo.addError(this.getClass(), "Debe informar identificación del equipo");
            logger.info("eliminar[FIN] no se informo la pk del equipo: {}", pk);
            return rtdo;
        }
        
        EquipoTO equipo = equipoPO.get(pk);
        if(equipo == null)
        {
            rtdo.addError(this.getClass(), "No existe equipo con código #{1}", pk.getCodigo());
            logger.info("eliminar[FIN] no se encontró registro del equipo: {}", pk);
            return rtdo;
        }
        
        if(!equipoPO.esEliminable(equipo))
        {
            rtdo.addError(this.getClass(),"TAG tiene registros relacionados" );
            logger.info("eliminar[FIN] equipo no es eliminable: {}", equipo);
            return rtdo;
        }

        equipoPO.eliminar(equipo);
        logger.info ("eliminar[FIN] registro eliminado con éxito: {}", equipo);
        return rtdo;
    }

    @Override
    public Respuesta<EquipoTO> get(EquipoTO pk)
    {
        logger.info("get[INI] pk: {}", pk);

        Resultado rtdo = new ResultadoProceso();

        if(pk==null || pk.isKeyBlank() )
        {
            rtdo.addError(this.getClass(), "Debe informar la identificación del Equipo");
            return Respuesta.of(rtdo);
        }
        
        EquipoTO equipo = equipoPO.get(pk);
        logger.info("get[FIN] registro encontrado: {}", equipo);
        return Respuesta.of(equipo);
    }

    @Override
    public List<EquipoTO> getVigentes(UbicacionTO pkUbicacion)
    {
        logger.info("getVigentes[INI] pkUbicacion: {}", pkUbicacion);
        
        if(pkUbicacion == null || pkUbicacion.isKeyBlank())
        {
            logger.info("getVigentes[FIN] no se informo la ubicación");
            return new ArrayList<>();
        }
        
        List<EquipoTO> lista = equipoPO.getList(pkUbicacion, true);
        logger.info("getVigentes[FIN] cantidad de equipos encontrados: {}", lista.size() );
        return lista;
    }

    @Override
    public List<EquipoTO> getTodos(UbicacionTO pkUbicacion)
    {
        logger.info("getTodos[INI] pkUbicacion: {}", pkUbicacion);
        
        if(pkUbicacion == null || pkUbicacion.isKeyBlank())
        {
            logger.info("getTodos[FIN] no se informo la ubicación");
            return new ArrayList<>();
        }
        
        List<EquipoTO> lista = equipoPO.getList(pkUbicacion, null);
        logger.info("getTodos[FIN] cantidad de equipos encontrados: {}", lista.size() );
        return lista;
    }

    @Transaccional
    @Override
    public Respuesta<TagTO> guardarTag(TagTO tag) throws Exception
    {
    	logger.info("guardarTag[INI] tag: {}", tag);
    	
    	Resultado rtdo = new ResultadoProceso();
    	if( tag == null )
    	{
    		rtdo.addError(this.getClass(), "Debe informar los datos del TAG");
    		logger.info("guardarTag[FIN] el tag llegó en NULL");
    		return Respuesta.of(rtdo);
    	}
    	
    	if(tag.getEnergiaCero() == null)
    	{
    		rtdo.addError(this.getClass(), "Debe informar la energía cero" );
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
    	if(StringUtils.isBlank(tag.getNombre()))
    	{
    		rtdo.addError(this.getClass(), "Debe informar el nombre del TAG" );
    	}
    	if(tag.getNumero() == null)
    	{
    		rtdo.addError(this.getClass(),  "Debe informar el N° del TAG" );
    	}
    	
    	if(!rtdo.esExitoso())
    	{
    		logger.info("guardarTag[FIN] hubo errores de validación: {}", rtdo);
    		return Respuesta.of(rtdo);
    	}
    	
    	TagTO otro = equipoPO.getTag(tag);
    	logger.debug("guardarTag[001] despues de buscar al TAG: {}", otro);

    	if( otro != null )
    	{
    		tag.setId(otro.getId());
    	}

    	equipoPO.guardarTag(tag);
    	logger.info("guardarTag[FIN] tag guardado con éxito: {}", tag);
    	return Respuesta.of(tag);
    }

    @Transaccional
    @Override
    public Resultado eliminarTag(TagTO pk) throws Exception
    {
        logger.info ("eliminarTag[INI] pkTag: {}", pk);
        
        Resultado rtdo = new ResultadoProceso();
        if(pk==null || pk.isKeyBlank())
        {
            rtdo.addError(this.getClass(), "Debe informar identificación del TAG");
            logger.info ("eliminarTag[FIN] no se informó completa la pk: {}", pk);
            return rtdo;
        }
        
        TagTO tag = equipoPO.getTag(pk);
        if( tag == null)
        {
            rtdo.addError(this.getClass(), "No existe TAG con N° #{1}", pk.getNumero() );
            logger.info("eliminarTag[FIN] no existe el TAG: {}", pk);
            return rtdo;
        }
        
        if( equipoPO.esTagEliminable(tag))
        {
            rtdo.addError(this.getClass(), "TAG tiene registros asociados");
            logger.info("eliminarTag[FIN] tag no es eliminable: {}", tag );
            return rtdo;
        }

        equipoPO.eliminarTag(tag);
        logger.info ("eliminarTag[FIN] registro elimiinado con éxito: {}", tag);        
        return rtdo;
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
        logger.info ("getTag[FIN] tag retornado: {}", tag);
        return Respuesta.of(tag);
    }
    
    @Override
    public List<TagTO> getTagsEnergiaCero(EquipoTO pk)
    {
        logger.info("getTagsEnergiaCero[INI] pk: {}", pk);
        
        if(pk==null || pk.isKeyBlank() )
        {
            logger.info("getTagsEnergiaCero[FIN] no se informó bien la pk del equipo: {}", pk);
            return new ArrayList<>();
        }
        
        List<TagTO> lista = equipoPO.getTags(pk, Boolean.TRUE, Boolean.TRUE);
        logger.info("getTagsEnergiaCero[FIN] registros retornados: {}", lista );
        return lista;
    }

    @Override
    public List<TagTO> getTagsVigentes(EquipoTO pk)
    {
        logger.info("getTagsVigentes[INI] pk: {}", pk);
        
        if(pk==null || pk.isKeyBlank() )
        {
            logger.info("getTagsVigentes[FIN] no se informó bien la pk del equipo: {}", pk);
            return new ArrayList<>();
        }
        
        List<TagTO> lista = equipoPO.getTags(pk, null, Boolean.TRUE);
        logger.info("getTagsVigentes[FIN] registros retornados: {}", lista );
        return lista;
    }
    
    @Override
    public List<TagTO> getTagsTodos(EquipoTO pk)
    {
        logger.info("getTagsTodos[INI] pk: {}", pk);
        
        if(pk==null || pk.isKeyBlank() )
        {
            logger.info("getTagsTodos[FIN] no se informó bien la pk del equipo: {}", pk);
            return new ArrayList<>();
        }
        
        List<TagTO> lista = equipoPO.getTags(pk, null, null);
        logger.info("getTagsTodos[FIN] registros retornados: {}", lista );
        return lista;
    }
}
