package cl.cerrocolorado.recob.po;

import cl.cerrocolorado.recob.po.map.RecobMap;
import cl.cerrocolorado.recob.to.entidades.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.entidades.DotacionLibroTO;
import cl.cerrocolorado.recob.to.entidades.EnergiaLibroTO;
import cl.cerrocolorado.recob.to.entidades.LibroBloqueoInfoTO;
import cl.cerrocolorado.recob.to.entidades.LibroBloqueoTO;
import cl.cerrocolorado.recob.to.entidades.RespLibroTO;
import cl.cerrocolorado.recob.to.entidades.TagLibroTO;
import java.util.Date;
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
public class LibroBloqueoPO implements BasePO<LibroBloqueoTO>
{
    private static final Logger logger = LogManager.getLogger(LibroBloqueoPO.class );
    
    @Autowired
    private RecobMap mapper;

    @Override
    public LibroBloqueoTO guardar(LibroBloqueoTO libro)
    {
        logger.info ("guardar[INI] libro: {}", libro);
     
        if(libro.isIdBlank())
        {
            mapper.insertLibro( libro );
            logger.debug("guadar[001] después de insertar el libro: {}", libro);
        } else
        {
            mapper.updateLibro( libro );
            logger.debug("guadar[002] después de actualizar el libro: {}", libro);
        }

        logger.info ("guardar[FIN] libro: {}", libro);        
        return libro;
    }

    @Override
    public void eliminar(LibroBloqueoTO pk)
    {
        logger.info ("delete[INI] pkLibro: {}", pk );
        mapper.deleteLibro(pk);
        logger.info ("delete[FIN] después de eliminar el registro: {}", pk );
    }

    @Override
    public boolean esEliminable(LibroBloqueoTO pk)
    {
        logger.info ("isDeleteable[INI] pkLibro: {}", pk);
        int relaciones = mapper.childsLibro(pk);
        logger.info ("isDeleteable[FIN] relaciones: {}", relaciones);

        return relaciones == 0;
    }
    
    @Override
    public LibroBloqueoTO obtener(LibroBloqueoTO pk)
    {
        logger.info ("obtener[INI] pk: {}", pk );
        
        Map<String, Object> parms = new HashMap<>();
        parms.put( "libro", pk );
        parms.put( "ubicacion", pk.getUbicacion());
        
        logger.debug("obtener[001] parametros: {}", parms);
        
        List<LibroBloqueoTO> lista = mapper.selectLibros( parms );
        logger.debug("obtener[002] despues de ejecutar el select: {}", lista.size() );
        
        if(lista.isEmpty())
        {
            logger.info ("obtener[FIN] no se encontró el registro: {}", parms );
            return null;
        }
        
        logger.info ("obtener[FIN] registro encontrado: {}", lista.get(0) );
        return lista.get(0);
    }
    
    public LibroBloqueoInfoTO obtenerLibro(LibroBloqueoTO pk)
    {
        logger.info ("obtener[INI] pkLibro: {}", pk);
        
        LibroBloqueoTO libro = obtener(pk);
        if( libro == null )
        {
            logger.info ("obtener[FIN] libro no encontrado: {}", pk);
            return null;
        }
        
        List<TagLibroTO> tags = obtenerTags(pk, Optional.empty());
        List<EnergiaLibroTO> energias = obtenerEnergias(pk);
        List<DotacionLibroTO> dotacion = obtenerDotaciones(pk);
        List<RespLibroTO> responsables = obtenerResponsables(pk);

        LibroBloqueoInfoTO l = new LibroBloqueoInfoTO();
        l.setCaja(libro.getCaja());
        l.setCerrado(libro.getCerrado());
        l.setDotacion(dotacion);
        l.setEnergias(energias);
        l.setFecha(libro.getFecha());
        l.setFechaCierre(libro.getFechaCierre());
        l.setId(libro.getId());
        l.setNumero(libro.getNumero());
        l.setResponsables(responsables);
        l.setTags(tags);

        logger.info ("obtener[FIN] registro encontrado: {}", l);
        return l;
    }

    public List<LibroBloqueoTO> obtenerList(CajaBloqueoTO pkCaja, 
                                            Optional<Boolean> vigencia, 
                                            Optional<Date> fechaLibro,
                                            Optional<Date> fechaCierre)
    {
        logger.info ("obtenerList[INI] pkCaja: {}", pkCaja);
        logger.info ("obtenerList[INI] vigencia: {}", vigencia );
        logger.info ("obtenerList[INI] fechaLibro: {}", fechaLibro );
        
        Map<String, Object> parms = new HashMap<>();
        parms.put( "ubicacion", pkCaja.getUbicacion());
        parms.put( "caja", pkCaja);
        parms.put( "vigencia", vigencia.orElse(null));
        parms.put( "fechaLibro", fechaLibro.orElse(null));
        parms.put( "fechaCierre", fechaCierre.orElse(null));
        logger.debug("obtenerList[001] parametros: {}", parms);

        List<LibroBloqueoTO> lista = mapper.selectLibros( parms );
        logger.debug("obtenerList[002] despues de ejecutar el select: {}", lista.size() );

        logger.info ("obtenerList[FIN] registros encontrados: {}", lista.size() );
        return lista;
    }
    
    public List<TagLibroTO> obtenerTags(LibroBloqueoTO pk,
                                    Optional<Boolean> energiaCero)
    {
        logger.info ("obtenerTags[INI] pkLibro: {}", pk);
        
        Map<String,Object> parms = new HashMap<>();
        parms.put("libro", pk);
        parms.put("energiaCero", energiaCero.orElse(null));
        logger.debug("obtenerTags[001] parámetros de la consulta: {}", parms);

        List<TagLibroTO> lista = mapper.selectTagsLibro(parms);

        logger.info ("obtenerTags[FIN] registros encontrados: {}", lista);
        return lista;
    }

    public TagLibroTO obtenerTag(TagLibroTO tag)
    {
        logger.info ("obtenerTag[INI] pkTag: {}", tag);
        
        Map<String,Object> parms = new HashMap<>();
        parms.put("libro", tag.getLibro());
        parms.put("tag", tag.getTag());
        parms.put("id", tag.getId());
        
        List<TagLibroTO> lista = mapper.selectTagsLibro(parms);
        if(lista.isEmpty())
        {
            logger.info ("obtenerTags[FIN] no se encontró registro: {}", tag);
            return null;
        }
        logger.info ("obtenerTags[FIN] registro encontrado: {}", lista.get(0));
        return lista.get(0);
    }

    public List<EnergiaLibroTO> obtenerEnergias(LibroBloqueoTO pk)
    {
        logger.info ("obtenerEnergias[INI] pkLibro: {}", pk);

        Map<String,Object> parms = new HashMap<>();
        parms.put("libro", pk);
        List<EnergiaLibroTO> lista = mapper.selectEnergiasLibro(parms);

        logger.info ("obtenerEnergias[FIN] registros encontrados: {}", lista);
        return lista;
    }

    public EnergiaLibroTO obtenerEnergia(EnergiaLibroTO pk)
    {
        logger.info ("obtenerEnergia[INI] pkEnergia: {}", pk);

        Map<String,Object> parms = new HashMap<>();
        parms.put("libro", pk.getLibro());
        parms.put("energia", pk);
        List<EnergiaLibroTO> lista = mapper.selectEnergiasLibro(parms);
        if(lista.isEmpty())
        {
            logger.info ("obtenerEnergia[FIN] no se encontró registro: {}", pk);
            return null;    
        }
        
        logger.info ("obtenerEnergia[FIN] registro encontrado: {}", lista.get(0));
        return lista.get(0);
    }

    public List<DotacionLibroTO> obtenerDotaciones(LibroBloqueoTO pk)
    {
        logger.info ("obtenerDotacion[INI] pkLibro: {}", pk);

        Map<String,Object> parms = new HashMap<>();
        parms.put("libro", pk);
        List<DotacionLibroTO> lista = mapper.selectDotacionLibro(parms);

        logger.info ("obtenerDotacion[FIN] registros encontrados: {}", lista);
        return lista;
    }

    public DotacionLibroTO obtenerDotacion(DotacionLibroTO pk)
    {
        logger.info ("obtenerDotacion[INI] pkDotacion: {}", pk);

        Map<String,Object> parms = new HashMap<>();
        parms.put("libro", pk.getLibro());
        parms.put("trabajador", pk.getTrabajador());
        parms.put("dotacion", pk);
        List<DotacionLibroTO> lista = mapper.selectDotacionLibro(parms);
        if(lista.isEmpty())
        {
            logger.info ("obtenerDotacion[FIN] no se encontró registro: {}", pk);
            return null;    
        }
        
        logger.info ("obtenerDotacion[FIN] registro encontrado: {}", lista.get(0));
        return lista.get(0);
    }

    public List<RespLibroTO> obtenerResponsables(LibroBloqueoTO pk)
    {
        logger.info ("obtenerResponsables[INI] pkLibro: {}", pk);

        Map<String,Object> parms = new HashMap<>();
        parms.put("libro", pk);
        List<RespLibroTO> lista = mapper.selectRespLibro(parms);

        logger.info ("obtenerResponsables[FIN] registros encontrados: {}", lista);
        return lista;
    }

    public TagLibroTO guardarTag(TagLibroTO tag)
    {
        logger.info ("guardarTag[INI] tag: {}", tag);

        if( tag.isIdBlank() )
        {
            mapper.insertTagLibro(tag);
            logger.debug("guardarTag[001] después de insertar el Tag: {}", tag);
        } else
        {
            mapper.updateTagLibro(tag);
            logger.debug("guardarTag[002] después de actualizar el Tag: {}", tag);            
        }

        logger.info ("guardarTag[FIN] Tag guardado con éxito: {}", tag);        
        return tag;
    }

    public void eliminarTag(TagLibroTO pk)
    {
        logger.info ("eliminarTag[INI] pk: {}", pk);
        mapper.deleteTagLibro(pk);
        logger.info ("eliminarTag[INI] registro eliminado con éxitok: {}", pk);
    }

    public DotacionLibroTO guardarDotacion(DotacionLibroTO dotacion)
    {
        logger.info ("guardarDotacion[INI] dotación: {}", dotacion);
        
        if(dotacion.isIdBlank())
        {
            mapper.insertDotacionLibro(dotacion);
            logger.debug ("guardarDotacion[001] después de instalar la dotación: {}", dotacion);
        } else
        {
            mapper.updateDotacionLibro(dotacion);
            logger.debug ("guardarDotacion[002] después de actualizar la dotación: {}", dotacion);            
        }

        logger.info ("guardarDotacion[FIN] dotación guardado con éxito: {}", dotacion);
        return dotacion;
    }
    
    public void eliminarDotacion(DotacionLibroTO pk)
    {
        logger.info ("eliminarDotacion[INI] pk: {}", pk);
        mapper.deleteDotacionLibro(pk);
        logger.info ("eliminarDotacion[FIN] registro eliminado con éxito: {}", pk);
    }

    public EnergiaLibroTO guardarEnergia(EnergiaLibroTO energia)
    {
        logger.info ("guardarEnergia[INI] energía: {}", energia);

        mapper.insertEnergiaLibro(energia);
        logger.debug("guardarEnergia[001] después de insertar la energía: {}", energia);

        logger.info ("guardarEnergia[FIN] energía guardads con éxito: {}", energia);
        return energia;
    }
    
    public void eliminarEnergia(EnergiaLibroTO pk)
    {
        logger.info("eliminarLibro[INI] pk: {}", pk);
        mapper.deleteEnergiaLibro(pk);
        logger.info("eliminarLibro[FIN] registro eliminado con éxito: {}", pk);
    }

    public RespLibroTO guardarResponsable(RespLibroTO responsable)
    {
        logger.info ("guardarResponsable[INI] responsable: {}", responsable);
        
        if(responsable.isIdBlank())
        {
            mapper.insertRespLibro(responsable);
            logger.debug("guardarResponsable[001] después de insertar al responsable: {}", responsable);
        } else
        {
            mapper.updateRespLibro(responsable);
            logger.debug("guardarResponsable[002] después de actualizar al responsable: {}", responsable);
        }
        
        logger.info ("guardarResponsable[FIN] responsable guardado con éxito: {}", responsable);
        return responsable;
    }

    public RespLibroTO obtenerRespLibro(RespLibroTO pk)
    {
        logger.info ("obtenerRespLibro[INI] pk: {}", pk);

        Map<String,Object> parms = new HashMap<>();
        parms.put("libro", pk);
        parms.put("trabajador", pk.getTrabajador());
        parms.put("responsable", pk);
        List<RespLibroTO> lista = mapper.selectRespLibro(parms);
        if(lista.isEmpty())
        {
            logger.info ("obtenerRespLibro[FIN] no se encontró registro: {}", pk);
            return null;    
        }
        
        logger.info ("obtenerRespLibro[FIN] registro encontrado: {}", lista.get(0));
        return lista.get(0);
    }

    public RespLibroTO obtenerRespVigente(LibroBloqueoTO pk)
    {
        logger.info ("obtenerRespVigente[INI] pk: {}", pk);

        Map<String,Object> parms = new HashMap<>();
        parms.put("libro", pk);
        parms.put("vigente", true);
        List<RespLibroTO> lista = mapper.selectRespLibro(parms);
        if(lista.isEmpty())
        {
            logger.info ("obtenerRespVigente[FIN] no se encontró registro: {}", pk);
            return null;    
        }
        
        logger.info ("obtenerRespVigente[FIN] registro encontrado: {}", lista.get(0));
        return lista.get(0);
    }
    
    public int obtenerNumeroLibro()
    {
        logger.info("obtenerNumeroLibro[INI]");
        
        int numero = mapper.selectNumeroLibro();
        
        logger.info("obtenerNumeroLibro[FIN] numero: {}", numero);
        return numero;
    }
}
