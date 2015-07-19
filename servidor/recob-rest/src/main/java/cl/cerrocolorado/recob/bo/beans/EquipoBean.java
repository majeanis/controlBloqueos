package cl.cerrocolorado.recob.bo.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cl.cerrocolorado.recob.bo.EquipoBO;
import cl.cerrocolorado.recob.bo.Transaccion;
import cl.cerrocolorado.recob.po.EquipoPO;
import cl.cerrocolorado.recob.to.EquipoTO;
import cl.cerrocolorado.recob.to.TagTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ResultadoProceso;
import cl.cerrocolorado.recob.utils.Transaccional;
import cl.cerrocolorado.recob.utils.mensajes.RegistrosQueryInfo;
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

    @Override
    public Respuesta<EquipoTO> guardar(EquipoTO equipo) throws Exception
    {
        logger.info("guardar[INI] equipo: {}", equipo);

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

        EquipoTO otro = equipoPO.get(equipo);
        logger.debug("guardar[001] después de buscar otro equipo: {}", otro);
        if (otro != null)
        {
            equipo.setId(otro.getId());
        }

        // Se utilizará control manual de transacciones, ya que internamente
        // llamaremos a un método que también será transaccional y eventualmente
        // necesitaremos hacer rollback, cuando haya fallas de validación
        Transaccion txLocal = new Transaccion();

        try
        {
            txLocal.begin();
            equipoPO.guardar(equipo);
            logger.debug("guardar[002] después de guardar el equipo: {}", equipo);
            
            List<TagTO> tags = equipo.getTags();
            for(int i=0; i < tags.size(); i++ )
            {
                TagTO tag = tags.get(i);
                tag.setIdEquipo(equipo.getId());

                Respuesta<TagTO> r = guardarTag(tag);
                if(!r.getResultado().esExitoso())
                {
                    logger.debug("guardar[003] errores al validar el TAG: {}", tag);
                    rtdo.append(r.getResultado(), "[indice:" + (i + 1) + "]");
                }
            }
            
            if(rtdo.esExitoso())
            {
                rtdo.addMensaje(this.getClass(), "Equipo guardado con éxito");
                txLocal.commit();
                logger.info("guardar[FIN] después del commit: {}", equipo);
                return Respuesta.of(rtdo, equipo);
            } else
            {
                txLocal.rollback();
                logger.info("guardar[FIN] después del rollback: {} {}", rtdo, equipo);
                return Respuesta.of(rtdo);
            }
        } catch(Exception e)
        {
            txLocal.rollback();
            logger.error("guardar[ERR] ", e);
            throw e;
        }
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
    	if(tag.getIdEquipo()==null)
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

        rtdo.addMensaje(this.getClass(), "TAG guardado con éxito");
        
    	logger.info("guardarTag[FIN] tag guardado con éxito: {}", tag);
    	return Respuesta.of(rtdo, tag);
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
    
        // Revisamos si es posible eliminar los TAGs
        for( TagTO tag: equipo.getTags())
        {
            if( !equipoPO.esTagEliminable(tag) )
            {
                rtdo.addError(this.getClass(), "TAG N° #{1} tiene registros asociados", String.valueOf(tag.getNumero()));
            }
        }

        if(!rtdo.esExitoso())
        {
            logger.info("eliminar[FIN] se encontraron errores de validación");
            return rtdo;
        }

        // Revisamos si es posible eliminar los TAGs
        for( TagTO tag: equipo.getTags())
        {
            if( !equipoPO.esTagEliminable(tag) )
            {
                rtdo.addError(this.getClass(), "TAG N° #{1} tiene registros asociados", String.valueOf(tag.getNumero()));
            }
        }
        
        equipoPO.eliminarTags(equipo);
        equipoPO.eliminar(equipo);
        
        rtdo.addMensaje(this.getClass(), "Registro eliminado con éxito");

        logger.info ("eliminar[FIN] registro eliminado con éxito: {}", equipo);
        return rtdo;
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
            rtdo.addError(this.getClass(), "No existe TAG con N° #{1}", String.valueOf(pk.getNumero()) );
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
        rtdo.addMensaje(this.getClass(), "Registro eliminado con éxito");
        
        logger.info ("eliminarTag[FIN] registro elimiinado con éxito: {}", tag);        
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
        if( equipo == null )
        {
            rtdo.addError(this.getClass(), "No existe equipo con código #{1}", pk.getCodigo());
        }

        logger.info("get[FIN] registro encontrado: {}", equipo);
        return Respuesta.of(equipo);
    }

    @Override
    public Respuesta<List<EquipoTO>> getTodos(UbicacionTO pkUbicacion, Boolean vigencia)
    {
        logger.info("getTodos[INI] pkUbicacion: {}", pkUbicacion);
        logger.info("getTodos[INI] vigencia: {}", vigencia);

        Resultado rtdo = new ResultadoProceso();
        if(pkUbicacion == null || pkUbicacion.isKeyBlank())
        {
            rtdo.addMensaje(this.getClass(), "Debe informar la ubicación" );
            logger.info("getTodos[FIN] no se informo la ubicación");
            return Respuesta.of(rtdo);
        }
        
        List<EquipoTO> lista = equipoPO.getList(pkUbicacion, vigencia);
        rtdo.addMensaje(new RegistrosQueryInfo(this.getClass(), lista.size()));

        logger.info("getTodos[FIN] cantidad de equipos encontrados: {}", lista.size() );
        return Respuesta.of(rtdo, lista);
    }

    @Override
    public Respuesta<List<EquipoTO>> getVigentes(UbicacionTO pkUbicacion)
    {
        return getTodos(pkUbicacion, true);
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
            rtdo.addError(this.getClass(), "No existe TAG N° #{1}", String.valueOf(pk.getNumero()));
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
        
        List<TagTO> lista = equipoPO.getTags(pk, Boolean.TRUE, Boolean.TRUE);
        rtdo.addMensaje(new RegistrosQueryInfo(this.getClass(), lista.size()));

        logger.info("getTagsEnergiaCero[FIN] registros retornados: {}", lista );
        return Respuesta.of(rtdo,lista);
    }

    @Override
    public Respuesta<List<TagTO>> getTagsTodos(EquipoTO pk, Boolean vigencia)
    {
        logger.info("getTagsTodos[INI] pk: {}", pk);
        logger.info("getTagsTodos[INI] vigencia: {}", vigencia);
        
        Resultado rtdo = new ResultadoProceso();
        if(pk==null || pk.isKeyBlank() )
        {
            rtdo.addError(this.getClass(), "Debe informar la ubicación" );
            logger.info("getTagsTodos[FIN] no se informó bien la pk del equipo: {}", pk);
            return Respuesta.of(rtdo);
        }
        
        List<TagTO> lista = equipoPO.getTags(pk, null, vigencia);
        rtdo.addMensaje(new RegistrosQueryInfo(this.getClass(), lista.size()));

        logger.info("getTagsTodos[FIN] registros retornados: {}", lista.size());
        return Respuesta.of(rtdo, lista);
    }

    @Override
    public Respuesta<List<TagTO>> getTagsVigentes(EquipoTO pk)
    {
        return getTagsTodos(pk, true);
    }
}
