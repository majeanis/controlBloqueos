package cl.cerrocolorado.recob.bo.beans;

import cl.cerrocolorado.recob.bo.LibroBloqueoBO;
import cl.cerrocolorado.recob.po.CajaBloqueoPO;
import cl.cerrocolorado.recob.po.LibroBloqueoPO;
import cl.cerrocolorado.recob.po.UbicacionPO;
import cl.cerrocolorado.recob.to.entidades.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.entidades.DotacionLibroTO;
import cl.cerrocolorado.recob.to.entidades.EnergiaLibroTO;
import cl.cerrocolorado.recob.to.entidades.LibroBloqueoInfoTO;
import cl.cerrocolorado.recob.to.entidades.LibroBloqueoTO;
import cl.cerrocolorado.recob.to.entidades.RespLibroTO;
import cl.cerrocolorado.recob.to.entidades.TagLibroTO;
import cl.cerrocolorado.recob.to.entidades.UbicacionTO;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ResultadoProceso;
import cl.cerrocolorado.recob.utils.Transaccional;
import cl.cerrocolorado.recob.utils.Utils;
import cl.cerrocolorado.recob.utils.mensajes.RegistrosQueryInfo;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author mauricio.camara
 */
@Scope("singleton")
@Service("libroBloqueoBO")
public class LibroBloqueoBean implements LibroBloqueoBO
{
    private static final Logger logger = LogManager.getLogger(LibroBloqueoBean.class);

    @Autowired
    private UbicacionPO ubicacionPO;

    @Autowired
    private LibroBloqueoPO libroBloqueoPO;
    
    @Autowired
    private CajaBloqueoPO cajaBloqueoPO;
    
    @Override
    @Transaccional
    public Respuesta<LibroBloqueoTO> save(LibroBloqueoTO libro)
    {
        logger.info("save[INI] libro: {}", libro);

        Resultado rtdo = new ResultadoProceso();

        if(libro == null)
        {
            rtdo.addError(this.getClass(), "Debe informar los datos del Libro");
            logger.info("save[FIN] no se informó el Libro");
            return Respuesta.of(rtdo);
        }
        if(libro.getCaja()==null || libro.getCaja().isKeyBlank())
        {
            rtdo.addError(this.getClass(), "Debe informar la Caja asociada al Libro");
        } else
        {
            CajaBloqueoTO caja = cajaBloqueoPO.get(libro.getCaja());
            if( caja == null )
            {
                rtdo.addError(this.getClass(), "Caja N° %d no existe", libro.getCaja().getNumero());
            }
            else
            {
                libro.setUbicacion(caja.getUbicacion());
                libro.setCaja(caja);
            }
        }
        if(libro.getFecha()==null)
        {
            rtdo.addError(this.getClass(), "Debe informar la fecha del libro");
        }
        if(libro.getCerrado()==null)
        {
            rtdo.addError(this.getClass(), "Debe informar si el libro está cerrado [Si/No]");
        } else if (libro.getCerrado() && libro.getFechaCierre() == null )
        {
            rtdo.addError(this.getClass(), "Debe informar la fecha de cierre del libro");
        } else if (!libro.getCerrado() && libro.getFechaCierre() != null )
        {
            rtdo.addError(this.getClass(), "No debe informar la Fecha de Cierre");
        }
        if (libro.getFecha() != null && 
            libro.getFechaCierre()!=null && 
            libro.getFecha().compareTo(libro.getFechaCierre()) > 0 )
        {
            rtdo.addError(this.getClass(), "La fecha de cierre no puede ser anterior a la fecha del libro");
        }
        
        boolean esNuevo = libro.isIdBlank();
        logger.debug("save[001] es un nuevo libro?: {}", esNuevo);
        
        if(esNuevo && Utils.nvl(libro.getNumero(),0) > 0 )
        {
            rtdo.addError(this.getClass(), "No debe informar el N° de Libro");
        }
        else if( !esNuevo && Utils.nvl( libro.getNumero(), 0) == 0)
        {
            rtdo.addError(this.getClass(), "Debe informar el N° de Libro" );
        }
        else if( !esNuevo )
        {
            LibroBloqueoTO otro = libroBloqueoPO.get(libro);
            if( otro == null )
            {
                rtdo.addError(this.getClass(), "No existe Libro con Número %d", libro.getNumero() );
            } else
            {
                libro.setNumero(otro.getNumero());
                libro.setFecha(otro.getFecha());
                libro.setUbicacion(otro.getUbicacion());
                libro.setCaja(otro.getCaja());
            }
        }

        logger.debug( "save[002] resultado de validaciones: {}", rtdo);
        if(!rtdo.esExitoso())
        {
            logger.info("save[FIN] se detectaron errores de validación");
            return Respuesta.of(rtdo);
        }

        if( esNuevo )
        {
            libro.setNumero(libroBloqueoPO.obtenerNumeroLibro());
            logger.debug("save[003] después de obtener un nuevo número de libro: {}", libro.getNumero() );
        }

        libroBloqueoPO.save(libro);
        rtdo.addMensaje(this.getClass(), "Libro guardado con éxito [N° %d]", libro.getNumero());
        
        logger.info("save[FIN] libro guardado con éxito: {}", libro);
        return Respuesta.of(libro);
    }

    @Override
    public Respuesta<LibroBloqueoTO> get(LibroBloqueoTO pk)
    {
        logger.info("get[INI] pk: {}", pk);
        
        Resultado rtdo = new ResultadoProceso();
        if(pk==null || pk.isKeyBlank())
        {
            rtdo.addError(this.getClass(), "Debe informar identificación del libro");
            logger.info("get[FIN] no se enviaron los valores de la pk: {}", pk);
            return Respuesta.of(rtdo);
        }
        
        LibroBloqueoTO libro = libroBloqueoPO.get(pk);
        if(libro == null)
        {
            rtdo.addError(this.getClass(), "No existe Libro con N° %d", pk.getNumero());
        }

        logger.info("get[FIN] registro retornado: {}", libro);
        return Respuesta.of(rtdo,libro);
    }

    @Override
    public Respuesta<LibroBloqueoInfoTO> getLibro(LibroBloqueoTO pk)
    {
        logger.info("getLibro[INI] pk: {}", pk);
        
        Resultado rtdo = new ResultadoProceso();
        if(pk==null || pk.isKeyBlank())
        {
            rtdo.addError(this.getClass(), "Debe informar identificación del libro");
            logger.info("getLibro[FIN] no se enviaron los valores de la pk: {}", pk);
            return Respuesta.of(rtdo);
        }
        
        LibroBloqueoInfoTO libro = libroBloqueoPO.obtenerLibro(pk);
        if(libro==null)
        {
            rtdo.addError(this.getClass(), "No existe Libro con N° %d", pk.getNumero());
        }
        logger.info("getLibro[FIN] registro retornado: {}", libro);
        return Respuesta.of(rtdo,libro);
    }

    @Override
    public Respuesta<List<LibroBloqueoTO>> getAbiertos(CajaBloqueoTO pk, Optional<Date> fechaLibro)
    {
        logger.info("getAbiertos[INI] pk: {}", pk);
        logger.info("getAbiertos[INI] fechaLibro: {}", fechaLibro);
        
        Resultado rtdo = new ResultadoProceso();
        if(pk==null || pk.isKeyBlank())
        {
            rtdo.addError(this.getClass(), "Debe informar identificación de la Caja");
            logger.info("getAbiertos[FIN] no se enviaron los valores de la pk: {}", pk);
            return Respuesta.of(rtdo);
        }
        
        List<LibroBloqueoTO> lista = libroBloqueoPO.obtenerList(pk, Optional.of(Boolean.TRUE), fechaLibro, Optional.empty());
        rtdo.addMensaje(new RegistrosQueryInfo(this.getClass(), lista.size()));

        logger.info("getAbiertos[FIN] registros retornados: {}", lista.size());
        return Respuesta.of(rtdo,lista);
    }

    @Override
    public Respuesta<List<LibroBloqueoTO>> getCerrados(CajaBloqueoTO pk, Optional<Date> fechaCierre)
    {
        logger.info("getCerrados[INI] pk: {}", pk);
        logger.info("getCerrados[INI] fechaCierre: {}", fechaCierre);
        
        Resultado rtdo = new ResultadoProceso();
        if(pk==null || pk.isKeyBlank())
        {
            rtdo.addError(this.getClass(), "Debe informar identificación de la Caja");
            logger.info("getCerrados[FIN] no se enviaron los valores de la pk: {}", pk);
            return Respuesta.of(rtdo);
        }
        
        List<LibroBloqueoTO> lista = libroBloqueoPO.obtenerList(pk, Optional.of(Boolean.FALSE), Optional.empty(), fechaCierre);
        rtdo.addMensaje(new RegistrosQueryInfo(this.getClass(), lista.size()));

        logger.info("getCerrados[FIN] registros retornados: {}", lista.size());
        return Respuesta.of(lista);
    }
    
    @Override
    public Respuesta<TagLibroTO> getTag(TagLibroTO pk)
    {
        logger.info("getTag[INI] pk: {}", pk);
        
        Resultado rtdo = new ResultadoProceso();
        if(pk==null || pk.isKeyBlank())
        {
            rtdo.addError(this.getClass(), "Debe informar la identificación del TAG" );
            logger.info("getTag[FIN] no se informaron los datos de la pk: {}", pk);
            return Respuesta.of(rtdo);
        }
        
        TagLibroTO tag = libroBloqueoPO.obtenerTag(pk);
        if(tag==null)
        {
            rtdo.addError(this.getClass(), "No existe TAG con código %s", pk.getTag().getCodigo());
        }

        logger.info("getTag[FIN] registro retornado: {}", tag);
        return Respuesta.of(rtdo,tag);
    }

    @Override
    public Respuesta<List<TagLibroTO>> getTags(LibroBloqueoTO pk, Optional<Boolean> energiaCero)
    {
        logger.info("getTags[INI] pk: {}", pk);
        logger.info("getTags[INI] energiaCero: {}", energiaCero);
        
        Resultado rtdo = new ResultadoProceso();
        if( pk==null || pk.isKeyBlank())
        {
            rtdo.addError(this.getClass(),  "Debe informar la identificación del Libro");
            logger.info("getTags[FIN] no se enviaron los valores de la pk: {}", pk);
            return Respuesta.of(rtdo);
        }
        
        List<TagLibroTO> lista = libroBloqueoPO.obtenerTags(pk, energiaCero);
        rtdo.addMensaje(new RegistrosQueryInfo(this.getClass(), lista.size()));

        logger.info("getTags[FIN] registros encontrados: {}", lista.size());
        return Respuesta.of(rtdo,lista);
    }

    @Override
    @Transaccional
    public Respuesta<TagLibroTO> eliminarTag(TagLibroTO pk) throws Exception
    {
        logger.info ("eliminarTag[INI] pk: {}", pk);
        
        Resultado rtdo = new ResultadoProceso();
        if(pk==null || pk.isKeyBlank())
        {
            rtdo.addError(this.getClass(),  "Debe informar la identificaión del tag");
            logger.info("eliminarTag[FIN] no se informó la identificación del tag: {}", pk);
            return Respuesta.of(rtdo);
        }
        
        TagLibroTO tag = libroBloqueoPO.obtenerTag(pk);
        if(tag==null)
        {
            rtdo.addError(this.getClass(), "No existe TAG: %s", pk.getTag().getCodigo());
            logger.info("eliminarTag[FIN] no existe el TAG que se desea eliminar: {}", pk);
            return Respuesta.of(rtdo);
        }
        
        libroBloqueoPO.eliminarTag(tag);
        rtdo.addMensaje(this.getClass(), "TAG eliminado con éxito");
        
        logger.info ("eliminarTag[FIN] pk: {}", pk);
        return Respuesta.of(rtdo,tag);
    }

    @Override
    public Respuesta<List<EnergiaLibroTO>> getEnergias(LibroBloqueoTO pk)
    {
        logger.info("getEnergias[INI] pk: {}", pk);
        
        Resultado rtdo = new ResultadoProceso();
        if( pk==null || pk.isKeyBlank())
        {
            rtdo.addError(this.getClass(),  "Debe informar la identificación del Libro");
            logger.info("getEnergias[FIN] no se enviaron los valores de la pk: {}", pk);
            return Respuesta.of(rtdo);
        }
        
        List<EnergiaLibroTO> lista = libroBloqueoPO.obtenerEnergias(pk);
        rtdo.addMensaje(new RegistrosQueryInfo(this.getClass(), lista.size()));

        logger.info("getEnergias[FIN] registros encontrados: {}", lista.size());
        return Respuesta.of(rtdo,lista);
    }

    @Deprecated
    @Transaccional
    public Respuesta<List<EnergiaLibroTO>> guardarEnergias(List<EnergiaLibroTO> energias) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transaccional
    public Respuesta<EnergiaLibroTO> eliminarEnergia(EnergiaLibroTO pk) throws Exception
    {
        logger.info("eliminarEnergia[INI] pk: {}", pk);
        
        Resultado rtdo = new ResultadoProceso();
        if(pk==null || pk.isKeyBlank())
        {
            rtdo.addError(this.getClass(), "Debe informar la identificación de la energía");
            logger.info("eliminarEnergia[FIN] no se informó completa la pk: {}", pk);
            return Respuesta.of(rtdo);
        }
        
        EnergiaLibroTO energia = libroBloqueoPO.obtenerEnergia(pk);
        if(energia==null)
        {
            rtdo.addError(this.getClass(), "No existe Energia: %s", pk.getNombre() );
            logger.info("eliminarEnergia[FIN] no existe registro de energía: {}", pk);
            return Respuesta.of(rtdo);
        }
        
        libroBloqueoPO.eliminarEnergia(energia);
        rtdo.addMensaje(this.getClass(), "Registro de la energía eliminado con éxito" );

        logger.info("eliminarEnergia[FIN] registro eliminado con éxito: {}", rtdo);
        return Respuesta.of(rtdo,energia);
    }

    @Override
    public Respuesta<List<DotacionLibroTO>> getDotaciones(LibroBloqueoTO pk)
    {
        logger.info("getDotaciones[INI] pk: {}", pk);
        
        Resultado rtdo = new ResultadoProceso();
        if( pk==null || pk.isKeyBlank())
        {
            rtdo.addError(this.getClass(),  "Debe informar la identificación del Libro");
            logger.info("getDotacion[FIN] no se enviaron los valores de la pk: {}", pk);
            return Respuesta.of(rtdo);
        }
        
        List<DotacionLibroTO> lista = libroBloqueoPO.obtenerDotaciones(pk);
        rtdo.addMensaje(new RegistrosQueryInfo(this.getClass(), lista.size()));

        logger.info("getDotacion[FIN] registros encontrados: {}", lista.size());
        return Respuesta.of(rtdo,lista);
    }
    
    @Override
    @Transaccional
    public Respuesta<DotacionLibroTO> eliminarDotacion(DotacionLibroTO pk)
    {
        logger.info("eliminarDotacion[INI] pk: {}", pk);
        
        Resultado rtdo = new ResultadoProceso();
        if(pk==null || pk.isKeyBlank())
        {
            rtdo.addError(this.getClass(), "Debe informar la identificación de la dotación");
            logger.info("eliminarDotacion[FIN] no se informó completa la pk: {}", pk);
            return Respuesta.of(rtdo);
        }
        
        DotacionLibroTO dotacion = libroBloqueoPO.obtenerDotacion(pk);
        if(dotacion==null)
        {
            rtdo.addError(this.getClass(), "No existe registro de dotacion: %s", pk.getTrabajador().getRut().toText() );
            logger.info("eliminarDotacion[FIN] no existe registro de dotacion: {}", pk);
            return Respuesta.of(rtdo);
        }
        
        libroBloqueoPO.eliminarDotacion(dotacion);
        rtdo.addMensaje(this.getClass(), "Registro de dotación eliminado con éxito" );

        logger.info("eliminarDotacion[FIN] registro eliminado con éxito: {}", rtdo);
        return Respuesta.of(rtdo,dotacion);
    }

    @Override
    public Respuesta<List<RespLibroTO>> getResponsables(LibroBloqueoTO pk)
    {
        logger.info("getResponsables[INI] pk: {}", pk);
        
        Resultado rtdo = new ResultadoProceso();
        if( pk==null || pk.isKeyBlank())
        {
            rtdo.addError(this.getClass(),  "Debe informar la identificación del Libro");
            logger.info("getResponsables[FIN] no se enviaron los valores de la pk: {}", pk);
            return Respuesta.of(rtdo);
        }
        
        List<RespLibroTO> lista = libroBloqueoPO.obtenerResponsables(pk);
        rtdo.addMensaje(new RegistrosQueryInfo(this.getClass(), lista.size()));

        logger.info("getResponsables[FIN] registros encontrados: {}", lista.size());
        return Respuesta.of(rtdo,lista);
    }

    @Deprecated
    @Override
    @Transaccional
    public Respuesta<RespLibroTO> asignarResponsable(RespLibroTO responsable) throws Exception
    {
        logger.info ("asignarResponsable[INI] responsable: {}", responsable);

        Resultado rtdo = new ResultadoProceso();
        if( responsable == null || responsable.isKeyBlank())
        {
            rtdo.addError(this.getClass(), "Debe informar la identificación del Responsable");
            return Respuesta.of(rtdo);
        }

        return null;
    }

    private Respuesta<TagLibroTO> guardarTag(TagLibroTO tag, boolean esNuevo) throws Exception
    {
        logger.info("guardarTag[INI] tag: {}", tag);
        logger.info("guardarTag[INI] esNuevo: {}", esNuevo);
        Resultado rtdo = new ResultadoProceso();
        
        if( tag==null)
        {
            rtdo.addError(this.getClass(), "Debe informar los datos del TAG");
            logger.info("guardarTag[FIN] no se informaron los datos del TAG");
            return Respuesta.of(rtdo);
        }
 
        if(tag.getLibro()==null|| tag.getLibro().isKeyBlank())
        {
            rtdo.addError(this.getClass(),"Debe informar el Libro al cual está asociado el TAG");
        }
        if(tag.getTag()==null || tag.getTag().isKeyBlank())
        {
            rtdo.addError(this.getClass(), "Debe informar la identificación del TAG");
        }
        if(tag.getEnergiaCero()==null)
        {
            rtdo.addError(this.getClass(), "Debe informar el atributo Energía Cero");
        }
        
        TagLibroTO otro = libroBloqueoPO.obtenerTag(tag);
        if(esNuevo)
        {
            if(otro!=null)
            {
                rtdo.addError(this.getClass(), "Ya existe TAG %s", tag.getTag().getCodigo());
            }
        }
        else if(otro==null)
        {
            rtdo.addError(this.getClass(), "No existe TAG %s", tag.getTag().getCodigo());
        } 
        else
        {
            tag.getTag().setId(otro.getTag().getId());
            tag.getTag().setDescripcion(otro.getTag().getDescripcion());
            tag.getTag().setEquipo(otro.getTag().getEquipo());
            tag.getTag().setVigente(otro.getTag().getVigente());
        }

        if(!rtdo.esExitoso())
        {
            logger.info("guardarTag[FIN] se encontraron errores de validación: {}", rtdo);
            return Respuesta.of(rtdo);
        }
        
        libroBloqueoPO.guardarTag(tag);
        rtdo.addMensaje(this.getClass(), "Registro guardado con éxito");

        logger.info("guardarTag[INI] registro guardado con éxito: {}", tag);
        return Respuesta.of(rtdo);
    }

    @Override
    @Transaccional    
    public Respuesta<TagLibroTO> crearTag(TagLibroTO tag) throws Exception
    {
        return guardarTag(tag,true);
    }

    @Override
    @Transaccional    
    public Respuesta<TagLibroTO> modificarTag(TagLibroTO tag) throws Exception
    {
        return guardarTag(tag,false);
    }

    @Deprecated
    public Respuesta<List<TagLibroTO>> guardarTags(List<TagLibroTO> tags) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Deprecated
    public Respuesta<EnergiaLibroTO> getEnergia(EnergiaLibroTO pk)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Deprecated
    private Respuesta<EnergiaLibroTO> guardarEnergia(LibroBloqueoTO pk, boolean esNuevo)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    @Transaccional
    public Respuesta<EnergiaLibroTO> crearEnergia(LibroBloqueoTO pk)
    {
        return guardarEnergia(pk,true);
    }

    @Override
    @Transaccional
    public Respuesta<EnergiaLibroTO> modificarEnergia(LibroBloqueoTO pk)
    {
        return guardarEnergia(pk,false);
    }

    @Deprecated
    private Respuesta<DotacionLibroTO> guardarDotacion(DotacionLibroTO dotacion, boolean esNuevo)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transaccional
    public Respuesta<DotacionLibroTO> crearDotacion(DotacionLibroTO dotacion) throws Exception
    {
        return guardarDotacion(dotacion,true);
    }

    @Override
    @Transaccional
    public Respuesta<DotacionLibroTO> modificarDotacion(DotacionLibroTO dotacion) throws Exception
    {
        return guardarDotacion(dotacion,false);
    }

    @Override
    @Transaccional
    public Respuesta<LibroBloqueoTO> delete(LibroBloqueoTO pk) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
