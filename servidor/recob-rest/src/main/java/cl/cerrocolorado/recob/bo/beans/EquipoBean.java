package cl.cerrocolorado.recob.bo.beans;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cl.cerrocolorado.recob.bo.EquipoBO;
import cl.cerrocolorado.recob.bo.utils.Transaccion;
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

    public Respuesta<EquipoTO> guardar(EquipoTO equipo, boolean esNuevo)
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
                rtdo.addError(this.getClass(), "Ya existe equipo con código #{1}", equipo.getCodigo());
            }
        } 
        else if ( otro == null )
        {
            rtdo.addError(this.getClass(), "No existe equipo con código #{1}", equipo.getCodigo());
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
    
    public Respuesta<EquipoTagsTO> guardar(EquipoTagsTO equipo, boolean esNuevo) throws Exception
    {
        logger.info("guardar[INI] equipo: {}", equipo);

        // Se utilizará control manual de transacciones, ya que internamente
        // llamaremos a un método que también será transaccional y eventualmente
        // necesitaremos hacer rollback, cuando haya fallas de validación
        Transaccion txLocal = new Transaccion();

        try
        {
            txLocal.begin();
           
            Respuesta<EquipoTO> re = guardar((EquipoTO) equipo);
            if( !re.getResultado().esExitoso() )
            {
                txLocal.rollback();
                logger.info("guardar[FIN] errores al guardar equipo: {}", re);
                return Respuesta.of(re.getResultado());
            }
            
            logger.debug("guardar[001] después de guardar el equipo: {}", equipo);

            Resultado rtdo = new ResultadoProceso();
            List<TagTO> tags = equipo.getTags();
            for(int i=0; i < tags.size(); i++ )
            {
                TagTO tag = tags.get(i);
                tag.setEquipo(equipo);

                Respuesta<TagTO> rt = guardarTag(tag);
                if(!rt.getResultado().esExitoso())
                {
                    logger.debug("guardar[003] errores al validar el TAG: {}", tag);
                    rtdo.append(rt.getResultado(), " [indice:" + (i + 1) + "]");
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
            logger.error("guardar[ERR] exception ", e);
            throw e;
        }
    }

    private Respuesta<TagTO> guardarTag(TagTO tag) throws Exception
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
            rtdo.addError(this.getClass(), "No existe equipo con código #{1}", pk.getCodigo());
            logger.info("eliminar[FIN] no se encontró registro del equipo: {}", pk);
            return Respuesta.of(rtdo);
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
            return Respuesta.of(rtdo);
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
        return Respuesta.of(rtdo,equipo);
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
            rtdo.addError(this.getClass(), "No existe TAG con N° #{1}", String.valueOf(pk.getNumero()) );
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
        
        logger.info ("eliminarTag[FIN] registro elimiinado con éxito: {}", tag);        
        return Respuesta.of(rtdo,tag);
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
            rtdo.addError(this.getClass(), "No existe equipo con código #{1}", pk.getCodigo());
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
        
        List<TagTO> lista = equipoPO.getTags(pk, Optional.of(Boolean.TRUE), Optional.of(Boolean.TRUE));
        rtdo.addMensaje(new RegistrosQueryInfo(this.getClass(), lista.size()));

        logger.info("getTagsEnergiaCero[FIN] registros retornados: {}", lista );
        return Respuesta.of(rtdo,lista);
    }

    @Override
    public Respuesta<List<TagTO>> getTags(EquipoTO pk, 
                                          Optional<Boolean> vigencia)
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
        
        List<TagTO> lista = equipoPO.getTags(pk, Optional.empty(), vigencia);
        rtdo.addMensaje(new RegistrosQueryInfo(this.getClass(), lista.size()));

        logger.info("getTagsTodos[FIN] registros retornados: {}", lista.size());
        return Respuesta.of(rtdo, lista);
    }

    @Override
    @Transaccional
    public Respuesta<TagTO> crearTag(TagTO tag) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Respuesta<TagTO> modificarTag(TagTO tag) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Respuesta<EquipoTO> get(EquipoTO pk)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
