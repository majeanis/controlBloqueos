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
    public LibroBloqueoTO save(LibroBloqueoTO libro)
    {
        logger.info ("save[INI] libro: {}", libro);
     
        if(libro.isIdBlank())
        {
            mapper.insertLibro( libro );
            logger.debug("save[001] después de insertar el libro: {}", libro);
        } else
        {
            mapper.updateLibro( libro );
            logger.debug("save[002] después de actualizar el libro: {}", libro);
        }

        logger.info ("save[FIN] libro: {}", libro);        
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
        long relaciones = mapper.childsLibro(pk);
        logger.info ("isDeleteable[FIN] relaciones: {}", relaciones);
        return relaciones == 0;
    }

    private LibroBloqueoTO get(Map<String,Object> parms)
    {
        logger.info ("get[INI] parametros: {}", parms);
        
        List<LibroBloqueoTO> lista = mapper.selectLibros( parms );
        logger.debug("get[001] despues de ejecutar el select: {}", lista.size() );
        
        if(lista.isEmpty())
        {
            logger.info ("get[FIN] no se encontró el registro: {}", parms );
            return null;
        }
        
        logger.info ("get[FIN] registro encontrado: {}", lista.get(0) );
        return lista.get(0);
    }
    
    @Override
    public LibroBloqueoTO get(LibroBloqueoTO pk)
    {
        logger.info ("get[INI] pk: {}", pk );
        
        Map<String, Object> parms = new HashMap<>();
        parms.put( "ubicacion", pk.getUbicacion());
        parms.put( "caja", pk.getCaja() );
        parms.put( "numero", pk.getNumero() );
        
        LibroBloqueoTO l = get( parms );
        logger.info ("get[FIN] registro encontrado: {}", l);
        return l;
    }

    @Override
    public LibroBloqueoTO getById(LibroBloqueoTO id)
    {
        logger.info ("get[INI] id: {}", id );
        
        Map<String, Object> parms = new HashMap<>();
        parms.put( "ubicacion", id.getUbicacion());
        parms.put( "id", id.getId() );
        
        LibroBloqueoTO l = get( parms );
        logger.info ("get[FIN] registro encontrado: {}", l);
        return l;
    }
    
    public LibroBloqueoInfoTO getLibro(LibroBloqueoTO pk)
    {
        logger.info ("getLibro[INI] pkLibro: {}", pk);
        
        LibroBloqueoTO libro = get(pk);
        if( libro == null )
        {
            logger.info ("getLibro[FIN] libro no encontrado: {}", pk);
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

        logger.info ("getLibro[FIN] registro encontrado: {}", l);
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
        parms.put("tagLibro", tag);
        parms.put("libro", tag.getLibro());
        parms.put("tag", tag.getTag());
        
        List<TagLibroTO> lista = mapper.selectTagsLibro(parms);
        if(lista.isEmpty())
        {
            logger.info ("getTag[FIN] no se encontró registro: {}", tag);
            return null;
        }
        logger.info ("getTag[FIN] registro encontrado: {}", lista.get(0));
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
        logger.info ("getDotaciones[INI] pkLibro: {}", pk);

        Map<String,Object> parms = new HashMap<>();
        parms.put("libro", pk);
        List<DotacionLibroTO> lista = mapper.selectDotacionLibro(parms);

        logger.info ("getDotaciones[FIN] registros encontrados: {}", lista);
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

    public TagLibroTO saveTag(TagLibroTO tag)
    {
        logger.info ("saveTag[INI] tag: {}", tag);

        if( tag.isIdBlank() )
        {
            mapper.insertTagLibro(tag);
            logger.debug("saveTag[001] después de insertar el Tag: {}", tag);
        } else
        {
            mapper.updateTagLibro(tag);
            logger.debug("saveTag[002] después de actualizar el Tag: {}", tag);            
        }

        logger.info ("saveTag[FIN] Tag guardado con éxito: {}", tag);        
        return tag;
    }

    public void deleteTag(TagLibroTO pk)
    {
        logger.info ("deleteTag[INI] pk: {}", pk);
        mapper.deleteTagLibro(pk);
        logger.info ("deleteTag[INI] registro eliminado con éxitok: {}", pk);
    }

    public DotacionLibroTO saveDotacion(DotacionLibroTO dotacion)
    {
        logger.info ("saveDotacion[INI] dotación: {}", dotacion);
        
        if(dotacion.isIdBlank())
        {
            mapper.insertDotacionLibro(dotacion);
            logger.debug ("saveDotacion[001] después de instalar la dotación: {}", dotacion);
        } else
        {
            mapper.updateDotacionLibro(dotacion);
            logger.debug ("saveDotacion[002] después de actualizar la dotación: {}", dotacion);            
        }

        logger.info ("saveDotacion[FIN] dotación guardado con éxito: {}", dotacion);
        return dotacion;
    }
    
    public void deleteDotacion(DotacionLibroTO pk)
    {
        logger.info ("deleteDotacion[INI] pk: {}", pk);
        mapper.deleteDotacionLibro(pk);
        logger.info ("deleteDotacion[FIN] registro eliminado con éxito: {}", pk);
    }

    public EnergiaLibroTO saveEnergia(EnergiaLibroTO energia)
    {
        logger.info ("saveEnergia[INI] energía: {}", energia);

        mapper.insertEnergiaLibro(energia);
        logger.debug("saveEnergia[001] después de insertar la energía: {}", energia);

        logger.info ("saveEnergia[FIN] energía guardads con éxito: {}", energia);
        return energia;
    }
    
    public void deleteEnergia(EnergiaLibroTO pk)
    {
        logger.info("deleteEnergia[INI] pk: {}", pk);
        mapper.deleteEnergiaLibro(pk);
        logger.info("deleteEnergia[FIN] registro eliminado con éxito: {}", pk);
    }

    public RespLibroTO saveResponsable(RespLibroTO responsable)
    {
        logger.info ("saveResponsable[INI] responsable: {}", responsable);
        
        if(responsable.isIdBlank())
        {
            mapper.insertRespLibro(responsable);
            logger.debug("saveResponsable[001] después de insertar al responsable: {}", responsable);
        } else
        {
            mapper.updateRespLibro(responsable);
            logger.debug("saveResponsable[002] después de actualizar al responsable: {}", responsable);
        }
        
        logger.info ("saveResponsable[FIN] responsable guardado con éxito: {}", responsable);
        return responsable;
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

    public RespLibroTO getRespVigente(LibroBloqueoTO pk)
    {
        logger.info ("getRespVigente[INI] pk: {}", pk);

        Map<String,Object> parms = new HashMap<>();
        parms.put("libro", pk);
        parms.put("vigente", true);
        List<RespLibroTO> lista = mapper.selectRespLibro(parms);
        if(lista.isEmpty())
        {
            logger.info ("getRespVigente[FIN] no se encontró registro: {}", pk);
            return null;    
        }
        
        logger.info ("getRespVigente[FIN] registro encontrado: {}", lista.get(0));
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
