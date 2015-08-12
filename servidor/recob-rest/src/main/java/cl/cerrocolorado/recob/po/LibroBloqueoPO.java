package cl.cerrocolorado.recob.po;

import cl.cerrocolorado.recob.po.map.RecobMap;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.DotacionLibroTO;
import cl.cerrocolorado.recob.to.EnergiaLibroTO;
import cl.cerrocolorado.recob.to.LibroBloqueoInfoTO;
import cl.cerrocolorado.recob.to.LibroBloqueoTO;
import cl.cerrocolorado.recob.to.RespLibroTO;
import cl.cerrocolorado.recob.to.TagLibroTO;
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
    public LibroBloqueoTO insert(LibroBloqueoTO libro)
    {
        logger.info ("insert[INI] libro: {}", libro);
        mapper.insertLibro( libro );
        logger.debug("insert[FIN] después de insertar el registro: {}", libro);

        return libro;
    }
    
    @Override
    public LibroBloqueoTO update(LibroBloqueoTO libro)
    {
        logger.info ("update[INI] libro: {}", libro);
        mapper.updateLibro( libro );
        logger.debug("update[FIN] después de actualizar el registro: {}", libro);
 
        return libro;
    }

    @Override
    public void delete(LibroBloqueoTO pk)
    {
        logger.info ("delete[INI] pkLibro: {}", pk );
        mapper.deleteLibro(pk);
        logger.info ("delete[FIN] después de eliminar el registro: {}", pk );
    }

    @Override
    public boolean isDeleteable(LibroBloqueoTO pk)
    {
        logger.info ("isDeleteable[INI] pkLibro: {}", pk);
        int relaciones = mapper.childsLibro(pk);
        logger.info ("isDeleteable[FIN] relaciones: {}", relaciones);

        return relaciones == 0;
    }
    
    @Override
    public LibroBloqueoTO get(LibroBloqueoTO pk)
    {
        logger.info ("get[INI] pk: {}", pk );
        
        Map<String, Object> parms = new HashMap<>();
        parms.put( "libro", pk );
        parms.put( "ubicacion", pk.getUbicacion());
        
        logger.debug("get[001] parametros: {}", parms);
        
        List<LibroBloqueoTO> lista = mapper.selectLibros( parms );
        logger.debug("get[002] despues de ejecutar el select: {}", lista.size() );
        
        if(lista.isEmpty())
        {
            logger.info ("get[FIN] no se encontró el registro: {}", parms );
            return null;
        }
        
        logger.info ("get[FIN] registro encontrado: {}", lista.get(0) );
        return lista.get(0);
    }
    
    public LibroBloqueoInfoTO getLibro(LibroBloqueoTO pk)
    {
        logger.info ("get[INI] pkLibro: {}", pk);
        
        LibroBloqueoTO libro = get(pk);
        if( libro == null )
        {
            logger.info ("get[FIN] libro no encontrado: {}", pk);
            return null;
        }
        
        List<TagLibroTO> tags = getTags(pk, Optional.empty());
        List<EnergiaLibroTO> energias = getEnergias(pk);
        List<DotacionLibroTO> dotacion = getDotaciones(pk);
        List<RespLibroTO> responsables = getResponsables(pk);

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

        logger.info ("get[FIN] registro encontrado: {}", l);
        return l;
    }

    public List<LibroBloqueoTO> getList(CajaBloqueoTO pkCaja, 
                                        Optional<Boolean> vigencia, 
                                        Optional<Date> fechaLibro,
                                        Optional<Date> fechaCierre)
    {
        logger.info ("getList[INI] pkCaja: {}", pkCaja);
        logger.info ("getList[INI] vigencia: {}", vigencia );
        logger.info ("getList[INI] fechaLibro: {}", fechaLibro );
        
        Map<String, Object> parms = new HashMap<>();
        parms.put( "ubicacion", pkCaja.getUbicacion());
        parms.put( "caja", pkCaja);
        parms.put( "vigencia", vigencia.orElse(null));
        parms.put( "fechaLibro", fechaLibro.orElse(null));
        parms.put( "fechaCierre", fechaCierre.orElse(null));
        logger.debug("getList[001] parametros: {}", parms);

        List<LibroBloqueoTO> lista = mapper.selectLibros( parms );
        logger.debug("getList[002] despues de ejecutar el select: {}", lista.size() );

        logger.info ("getList[FIN] registros encontrados: {}", lista.size() );
        return lista;
    }
    
    public List<TagLibroTO> getTags(LibroBloqueoTO pk,
                                    Optional<Boolean> energiaCero)
    {
        logger.info ("getTags[INI] pkLibro: {}", pk);
        
        Map<String,Object> parms = new HashMap<>();
        parms.put("libro", pk);
        parms.put("energiaCero", energiaCero.orElse(null));
        logger.debug("getTags[001] parámetros de la consulta: {}", parms);

        List<TagLibroTO> lista = mapper.selectTagsLibro(parms);

        logger.info ("getTags[FIN] registros encontrados: {}", lista);
        return lista;
    }

    public TagLibroTO getTag(TagLibroTO tag)
    {
        logger.info ("getTag[INI] pkTag: {}", tag);
        
        Map<String,Object> parms = new HashMap<>();
        parms.put("libro", tag.getLibro());
        parms.put("tag", tag.getTag());
        parms.put("id", tag.getId());
        
        List<TagLibroTO> lista = mapper.selectTagsLibro(parms);
        if(lista.isEmpty())
        {
            logger.info ("getTags[FIN] no se encontró registro: {}", tag);
            return null;
        }
        logger.info ("getTags[FIN] registro encontrado: {}", lista.get(0));
        return lista.get(0);
    }

    public List<EnergiaLibroTO> getEnergias(LibroBloqueoTO pk)
    {
        logger.info ("getEnergias[INI] pkLibro: {}", pk);

        Map<String,Object> parms = new HashMap<>();
        parms.put("libro", pk);
        List<EnergiaLibroTO> lista = mapper.selectEnergiasLibro(parms);

        logger.info ("getEnergias[FIN] registros encontrados: {}", lista);
        return lista;
    }

    public EnergiaLibroTO getEnergia(EnergiaLibroTO pk)
    {
        logger.info ("getEnergia[INI] pkEnergia: {}", pk);

        Map<String,Object> parms = new HashMap<>();
        parms.put("libro", pk.getLibro());
        parms.put("energia", pk);
        List<EnergiaLibroTO> lista = mapper.selectEnergiasLibro(parms);
        if(lista.isEmpty())
        {
            logger.info ("getEnergia[FIN] no se encontró registro: {}", pk);
            return null;    
        }
        
        logger.info ("getEnergia[FIN] registro encontrado: {}", lista.get(0));
        return lista.get(0);
    }

    public List<DotacionLibroTO> getDotaciones(LibroBloqueoTO pk)
    {
        logger.info ("getDotacion[INI] pkLibro: {}", pk);

        Map<String,Object> parms = new HashMap<>();
        parms.put("libro", pk);
        List<DotacionLibroTO> lista = mapper.selectDotacionLibro(parms);

        logger.info ("getDotacion[FIN] registros encontrados: {}", lista);
        return lista;
    }

    public DotacionLibroTO getDotacion(DotacionLibroTO pk)
    {
        logger.info ("getDotacion[INI] pkDotacion: {}", pk);

        Map<String,Object> parms = new HashMap<>();
        parms.put("libro", pk.getLibro());
        parms.put("trabajador", pk.getTrabajador());
        parms.put("dotacion", pk);
        List<DotacionLibroTO> lista = mapper.selectDotacionLibro(parms);
        if(lista.isEmpty())
        {
            logger.info ("getDotacion[FIN] no se encontró registro: {}", pk);
            return null;    
        }
        
        logger.info ("getDotacion[FIN] registro encontrado: {}", lista.get(0));
        return lista.get(0);
    }

    public List<RespLibroTO> getResponsables(LibroBloqueoTO pk)
    {
        logger.info ("getResponsables[INI] pkLibro: {}", pk);

        Map<String,Object> parms = new HashMap<>();
        parms.put("libro", pk);
        List<RespLibroTO> lista = mapper.selectRespLibro(parms);

        logger.info ("getResponsables[FIN] registros encontrados: {}", lista);
        return lista;
    }
    
    public TagLibroTO guardarTag(TagLibroTO pk)
    {
        logger.info ("guardarTag[INI] tag: {}", pk);
        
        if(pk.isIdBlank())
        {
            mapper.insertTagLibro(pk);
        } else
        {
            mapper.updateTagLibro(pk);
        }    
            
        logger.info ("guardarTag[FIN] registro guardado con éxito: {}", pk);
        return pk;
    }

    public void eliminarTag(TagLibroTO pk)
    {
        logger.info ("eliminarTag[INI] pk: {}", pk);
        mapper.deleteTagLibro(pk);
        logger.info ("eliminarTag[INI] registro eliminado con éxitok: {}", pk);
    }

    public DotacionLibroTO guardarDotacion(DotacionLibroTO pk)
    {
        logger.info ("guardarDotacion[INI] pk: {}", pk);
        
        if(pk.isIdBlank())
        {
            mapper.insertDotacionLibro(pk);
        } else
        {
            mapper.updateDotacionLibro(pk);
        }

        logger.info ("guardarDotacion[FIN] registro guardado con éxito: {}", pk);
        return pk;
    }
    
    public void eliminarDotacion(DotacionLibroTO pk)
    {
        logger.info ("eliminarDotacion[INI] pk: {}", pk);
        mapper.deleteDotacionLibro(pk);
        logger.info ("eliminarDotacion[FIN] registro eliminado con éxito: {}", pk);
    }
    
    public EnergiaLibroTO guardarEnergia(EnergiaLibroTO energia)
    {
        logger.info ("guardarEnergia[INI] pk: {}", energia);
        mapper.insertEnergiaLibro(energia);
        logger.info ("guardarEnergia[FIN] registro guardado con éxito: {}", energia);
        return energia;
    }
    
    public void eliminarEnergia(EnergiaLibroTO pk)
    {
        logger.info("eliminarLibro[INI] pk: {}", pk);
        mapper.deleteEnergiaLibro(pk);
        logger.info("eliminarLibro[FIN] registro eliminado con éxito: {}", pk);
    }

    public RespLibroTO guardarResponsable(RespLibroTO pk)
    {
        logger.info ("guardarResponsable[INI] pk: {}", pk);
        
        if(pk.isIdBlank())
        {
            mapper.insertRespLibro(pk);
        } else
        {
            mapper.updateRespLibro(pk);
        }

        logger.info ("guardarResponsable[FIN] registro guardado con éxito: {}", pk);
        return pk;
    }

    public RespLibroTO getRespLibro(RespLibroTO pk)
    {
        logger.info ("getRespLibro[INI] pk: {}", pk);

        Map<String,Object> parms = new HashMap<>();
        parms.put("libro", pk);
        parms.put("trabajador", pk.getTrabajador());
        parms.put("responsable", pk);
        List<RespLibroTO> lista = mapper.selectRespLibro(parms);
        if(lista.isEmpty())
        {
            logger.info ("getRespLibro[FIN] no se encontró registro: {}", pk);
            return null;    
        }
        
        logger.info ("getRespLibro[FIN] registro encontrado: {}", lista.get(0));
        return lista.get(0);
    }

    public RespLibroTO getRespLibroVigente(LibroBloqueoTO pk)
    {
        logger.info ("getRespLibroVigente[INI] pk: {}", pk);

        Map<String,Object> parms = new HashMap<>();
        parms.put("libro", pk);
        parms.put("vigente", true);
        List<RespLibroTO> lista = mapper.selectRespLibro(parms);
        if(lista.isEmpty())
        {
            logger.info ("getRespLibroVigente[FIN] no se encontró registro: {}", pk);
            return null;    
        }
        
        logger.info ("getRespLibroVigente[FIN] registro encontrado: {}", lista.get(0));
        return lista.get(0);
    }
    
    public int getNumeroLibro()
    {
        logger.info("getNumeroLibro[INI]");
        
        int numero = mapper.selectNumeroLibro();
        
        logger.info("getNumeroLibro[FIN] numero: {}", numero);
        return numero;
    }
}
