package cl.cerrocolorado.recob.bo.beans;

import cl.cerrocolorado.recob.bo.LibroBloqueoBO;
import cl.cerrocolorado.recob.po.CajaBloqueoPO;
import cl.cerrocolorado.recob.po.LibroBloqueoPO;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.DotacionLibroTO;
import cl.cerrocolorado.recob.to.EnergiaLibroTO;
import cl.cerrocolorado.recob.to.LibroBloqueoInfoTO;
import cl.cerrocolorado.recob.to.LibroBloqueoTO;
import cl.cerrocolorado.recob.to.RespLibroTO;
import cl.cerrocolorado.recob.to.TagLibroTO;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ResultadoProceso;
import cl.cerrocolorado.recob.utils.Transaccional;
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
    private LibroBloqueoPO libroBloqueoPO;
    
    @Autowired
    private CajaBloqueoPO cajaBloqueoPO;
    
    @Override
    @Transaccional
    public Respuesta<LibroBloqueoTO> guardar(LibroBloqueoTO libro) throws Exception
    {
        logger.info("guardar[INI] libro: {}", libro);
        
        Resultado rtdo = new ResultadoProceso();

        if(libro == null)
        {
            rtdo.addError(this.getClass(), "Debe informar los datos del Libro");
            logger.info("guardar[FIN] no se informó el Libro");
            return Respuesta.of(rtdo);
        }
        if(libro.getUbicacion()==null)
        {
            rtdo.addError(this.getClass(), "Debe informar la ubicación" );
        } else if( libro.getCaja() != null )
        {
            libro.getCaja().setUbicacion(libro.getUbicacion());
        }
        if(libro.getCaja() == null || libro.getCaja().isKeyBlank())
        {
            rtdo.addError(this.getClass(), "Debe informar la Caja asociada al Libro");
        } else
        {
            CajaBloqueoTO caja = cajaBloqueoPO.get(libro.getCaja());
            if( caja == null )
            {
                rtdo.addError(this.getClass(), "Caja informada no existe");
            }
            libro.setCaja(caja);
        }
        if(libro.getFecha()==null)
        {
            rtdo.addError(this.getClass(), "Debe informar la fecha del libro");
        }
        if(libro.getCerrado()==null)
        {
            rtdo.addError(this.getClass(), "Debe informar si el libro está cerrado");
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

        // Los libros son autoenumerados, por lo tanto, si el objeto viene con un
        // N° entonces se asumirá que se intenta actualizar un libro existente.
        // Por lo tanto, si el objeto llega sin Id y sin Número, entonces se trata
        // de un nuevo libro y en consecuencia, se le asigna un nuevo número
        if(libro.getId()==null && libro.getNumero()==null)
        {
            libro.setNumero(libroBloqueoPO.getNumeroLibro());
        } else
        {
            LibroBloqueoTO otro = libroBloqueoPO.get(libro);
            if( otro == null )
            {
                rtdo.addError(this.getClass(), "No existe Libro con Número #{1}", String.valueOf(libro.getNumero()) );
            } else
            {
                libro.setId(otro.getId());
                libro.setNumero(otro.getNumero());
                libro.setFecha(otro.getFecha());
            }
        }
    
        if(!rtdo.esExitoso())
        {
            logger.info("guardar[FIN] se detectaron errores de validación");
            return Respuesta.of(rtdo);
        }

        libroBloqueoPO.guardar(libro);
        rtdo.addMensaje(this.getClass(), "Libro guardado con éxito");
        
        logger.info("guardar[FIN] libro guardado con éxito: {}", libro);
        return Respuesta.of(libro);
    }

    @Override
    @Transaccional    
    public Respuesta<LibroBloqueoInfoTO> guardarLibro(LibroBloqueoInfoTO libro) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        logger.info("get[FIN] registro retornado: {}", libro);
        return Respuesta.of(libro);
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
        
        LibroBloqueoInfoTO libro = libroBloqueoPO.getLibro(pk);
        logger.info("getLibro[FIN] registro retornado: {}", libro);
        return Respuesta.of(libro);
    }

    @Override
    public Respuesta<List<LibroBloqueoTO>> getVigentes(CajaBloqueoTO pk, Optional<Date> fechaLibro)
    {
        logger.info("getVigentes[INI] pkCaja: {}", pk);
        logger.info("getVigentes[INI] fechaLibro: {}", fechaLibro);
        
        Resultado rtdo = new ResultadoProceso();
        if(pk==null || pk.isKeyBlank())
        {
            rtdo.addError(this.getClass(), "Debe informar identificación de la Caja");
            logger.info("getVigentes[FIN] no se enviaron los valores de la pk: {}", pk);
            return Respuesta.of(rtdo);
        }
        
        List<LibroBloqueoTO> lista = libroBloqueoPO.getList(pk, Optional.of(Boolean.TRUE), fechaLibro, null);
        rtdo.addMensaje(new RegistrosQueryInfo(this.getClass(), lista.size()));

        logger.info("getVigentes[FIN] registros retornados: {}", lista.size());
        return Respuesta.of(rtdo,lista);
    }

    @Override
    public Respuesta<List<LibroBloqueoTO>> getCerrados(CajaBloqueoTO pk, Optional<Date> fechaCierre)
    {
        logger.info("getCerrados[INI] pkCaja: {}", pk);
        logger.info("getCerrados[INI] fechaCierre: {}", fechaCierre);
        
        Resultado rtdo = new ResultadoProceso();
        if(pk==null || pk.isKeyBlank())
        {
            rtdo.addError(this.getClass(), "Debe informar identificación de la Caja");
            logger.info("getCerrados[FIN] no se enviaron los valores de la pk: {}", pk);
            return Respuesta.of(rtdo);
        }
        
        List<LibroBloqueoTO> lista = libroBloqueoPO.getList(pk, Optional.of(Boolean.FALSE), null, fechaCierre);
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
            rtdo.addError(this.getClass(),  "Debe informar la identificación del TAG" );
            logger.info("getTag[FIN] no se informaron los datos de la pk: {}", pk);
            return Respuesta.of(rtdo);
        }
        
        logger.info("getTag[FIN] registro retornado: {}", tag);
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
        
        List<TagLibroTO> lista = libroBloqueoPO.getTags(pk, energiaCero);
        rtdo.addMensaje(new RegistrosQueryInfo(this.getClass(), lista.size()));

        logger.info("getTags[FIN] registros encontrados: {}", lista.size());
        return Respuesta.of(rtdo,lista);
    }

    @Override
    @Transaccional
    public Resultado eliminarTag(TagLibroTO pk) throws Exception
    {
        logger.info ("eliminarTag[INI] pk: {}", pk);
        
        Resultado rtdo = new ResultadoProceso();
        if(pk==null || pk.isKeyBlank())
        {
            rtdo.addError(this.getClass(),  "Debe informar la identificaión del tag");
            logger.info("eliminarTag[FIN] no se informó la identificación del tag: {}", pk);
            return rtdo;
        }
        
        TagLibroTO tag = libroBloqueoPO.getTag(pk);
        if(tag==null)
        {
            rtdo.addError(this.getClass(),  "No existe TAG con N° #{1}", String.valueOf(pk.getTag().getNumero()));
            logger.info("eliminarTag[FIN] no existe el TAG que se desea eliminar: {}", pk);
            return rtdo;
        }
        
        libroBloqueoPO.eliminarTag(tag);
        rtdo.addMensaje(this.getClass(), "TAG eliminado con éxito");
        
        logger.info ("eliminarTag[FIN] pk: {}", pk);
        return rtdo;
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
        
        List<EnergiaLibroTO> lista = libroBloqueoPO.getEnergias(pk);
        rtdo.addMensaje(new RegistrosQueryInfo(this.getClass(), lista.size()));

        logger.info("getEnergias[FIN] registros encontrados: {}", lista.size());
        return Respuesta.of(rtdo,lista);
    }

    @Override
    @Transaccional
    public Respuesta<List<EnergiaLibroTO>> guardarEnergias(List<EnergiaLibroTO> energias) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transaccional
    public Resultado eliminarEnergia(EnergiaLibroTO pk) throws Exception
    {
        logger.info("eliminarEnergia[INI] pk: {}", pk);
        
        Resultado rtdo = new ResultadoProceso();
        if(pk==null || pk.isKeyBlank())
        {
            rtdo.addError(this.getClass(), "Debe informar la identificación de la energía");
            logger.info("eliminarEnergia[FIN] no se informó completa la pk: {}", pk);
        }
        
        EnergiaLibroTO energia = libroBloqueoPO.getEnergia(pk);
        if(energia==null)
        {
            rtdo.addError(this.getClass(), "No existe Energia: #{1}", pk.getNombre() );
            logger.info("eliminarEnergia[FIN] no existe registro de energía: {}", pk);
            return rtdo;
        }
        
        libroBloqueoPO.eliminarEnergia(pk);
        rtdo.addMensaje(this.getClass(), "Registro de la energía eliminado con éxito" );

        logger.info("eliminarEnergia[FIN] registro eliminado con éxito: {}", rtdo);
        return rtdo;
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
        
        List<DotacionLibroTO> lista = libroBloqueoPO.getDotaciones(pk);
        rtdo.addMensaje(new RegistrosQueryInfo(this.getClass(), lista.size()));

        logger.info("getDotacion[FIN] registros encontrados: {}", lista.size());
        return Respuesta.of(rtdo,lista);
    }

    @Override
    @Transaccional
    public Respuesta<DotacionLibroTO> guardarDotacion(DotacionLibroTO dotacion) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transaccional
    public Resultado eliminarDotacion(DotacionLibroTO pk)
    {
        logger.info("eliminarDotacion[INI] pk: {}", pk);
        
        Resultado rtdo = new ResultadoProceso();
        if(pk==null || pk.isKeyBlank())
        {
            rtdo.addError(this.getClass(), "Debe informar la identificación de la dotación");
            logger.info("eliminarDotacion[FIN] no se informó completa la pk: {}", pk);
        }
        
        DotacionLibroTO dotacion = libroBloqueoPO.getDotacion(pk);
        if(dotacion==null)
        {
            rtdo.addError(this.getClass(), "No existe registro de dotacion: #{1}", pk.getTrabajador().getRut().toText() );
            logger.info("eliminarDotacion[FIN] no existe registro de dotacion: {}", pk);
            return rtdo;
        }
        
        libroBloqueoPO.eliminarDotacion(pk);
        rtdo.addMensaje(this.getClass(), "Registro de dotación eliminado con éxito" );

        logger.info("eliminarDotacion[FIN] registro eliminado con éxito: {}", rtdo);
        return rtdo;
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
        
        List<RespLibroTO> lista = libroBloqueoPO.getResponsables(pk);
        rtdo.addMensaje(new RegistrosQueryInfo(this.getClass(), lista.size()));

        logger.info("getResponsables[FIN] registros encontrados: {}", lista.size());
        return Respuesta.of(rtdo,lista);
    }

    @Override
    @Transaccional
    public Respuesta<RespLibroTO> asignarResponsable(RespLibroTO responsable) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Respuesta<TagLibroTO> guardarTag(TagLibroTO tag) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
