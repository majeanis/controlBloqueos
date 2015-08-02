package cl.cerrocolorado.recob.rest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.cerrocolorado.recob.bo.UbicacionBO;
import cl.cerrocolorado.recob.bo.utils.FactoryBO;
import cl.cerrocolorado.recob.rest.utils.RespGenerica;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.LibroBloqueoInfoTO;
import cl.cerrocolorado.recob.to.LibroBloqueoTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.JsonUtils;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.utils.mensajes.ParsearJsonError;
import javax.ws.rs.FormParam;

@Path("controlBloqueos")
public class ControlBloqueosService
{
    private static final Logger logger = LogManager.getLogger(ControlBloqueosService.class);
    
    private static final UbicacionBO ubicacionBO;
    // private static final String token = "ec4b8544-1c73-11e5-9840-080027465435";

    private static final int VER_TODOS = 1;
    private static final int VER_VIGENTES = 2;
    private static final int VER_UNO = 3;
    private static final int VER_CERRADOS = 4;
    
    static
    {
        ubicacionBO = FactoryBO.getUbicacionBO();
    }

    @Path("{numeroCaja}/libros/abiertos")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verLibrosAbiertos(
            @HeaderParam("token") String tokenUbicacion,
            @PathParam  ("numeroCaja") Integer numeroCaja,
            @QueryParam ("fechaInicial") Date fechaInicial)
    {
    	logger.info ("verLibrosAbiertos[INI] token: {}", tokenUbicacion);
        logger.info ("verLibrosAbiertos[INI] numeroCaja: {}", numeroCaja);
        logger.info ("verLibrosAbiertos[INI] fechaInicial: {}", fechaInicial);
        
        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verLibrosAbiertos[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try
        {
            UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
            CajaBloqueoTO caja = new CajaBloqueoTO();
            caja.setUbicacion(ubicacion);
            caja.setNumero(numeroCaja);
            logger.debug("verLibrosAbiertos[001] objeto caja con el que se buscará el libro: {}", caja);

            Respuesta<List<LibroBloqueoTO>> r = FactoryBO.getLibroBloqueoBO().getVigentes(caja, Optional.ofNullable(fechaInicial));
            logger.info("verLibrosAbiertos[FIN] retorno de registros vigentes: {}", r);
            return RespGenerica.of(r);
        } catch(Exception e)
        {
            logger.error("verLibrosAbiertos[ERR] exception: ", e);
            return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("{numeroCaja}/libros/cerrados")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verLibrosCerrados(
            @HeaderParam("token") String tokenUbicacion,
            @PathParam  ("numeroCaja") Integer numeroCaja,
            @QueryParam ("fechaInicial") Date fechaInicial)
    {
    	logger.info ("verLibrosCerrados[INI] token: {}", tokenUbicacion);
        logger.info ("verLibrosCerrados[INI] numeroCaja: {}", numeroCaja);
        logger.info ("verLibrosCerrados[INI] fechaInicial: {}", fechaInicial);
        
        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verLibrosCerrados[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try
        {
            UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
            CajaBloqueoTO caja = new CajaBloqueoTO();
            caja.setUbicacion(ubicacion);
            caja.setNumero(numeroCaja);
            logger.debug("verLibrosCerrados[001] objeto caja con el que se buscará el libro: {}", caja);

            Respuesta<List<LibroBloqueoTO>> r = FactoryBO.getLibroBloqueoBO().getCerrados(caja, Optional.ofNullable(fechaInicial));
            logger.info("verLibrosCerrados[FIN] retorno de registros vigentes: {}", r);
            return RespGenerica.of(r);
        } catch(Exception e)
        {
            logger.error("verLibrosCerrados[ERR] exception: ", e);
            return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("{numeroCaja}/libros")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @PUT
    public RespGenerica guardarLibro(
    		@HeaderParam("token") String tokenUbicacion,
            @PathParam  ("numeroCaja") Integer numeroCaja,
    		@FormParam  ("libro") String jsonLibro)
    {
    	logger.info ("guardarLibro[INI] token: {}", tokenUbicacion);
        logger.info ("guardarLibro[INI] numeroCaja: {}", numeroCaja);
    	logger.info ("guardarLibro[INI] jsonLibro: {}", jsonLibro);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("guardarLibro[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try 
        {
            UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
            LibroBloqueoTO libro = JsonUtils.fromJson(jsonLibro, LibroBloqueoTO.class);
            if( libro == null )
            {
                logger.info("guardarLibro[FIN] no se pudo parsear el JSON: {}", jsonLibro);
                return RespGenerica.of(new ParsearJsonError(this.getClass(),"Libro"));
            }

            logger.debug("guardarLibro[001] después de parsear el JSON: {}", libro);

            libro.setUbicacion(ubicacion);
            libro.setCaja( new CajaBloqueoTO() );
            libro.getCaja().setNumero(numeroCaja);
            libro.getCaja().setUbicacion(ubicacion);
            logger.debug("guardarLibro[002] antes de llamar al método BO: {}", libro);

            Respuesta<LibroBloqueoTO> r = FactoryBO.getLibroBloqueoBO().guardar(libro);

            logger.info("guardarLibro[FIN] resultado registro libro: {}", r);
            return RespGenerica.of(r);
        } catch (Exception e) 
        {
			logger.error("guardarLibro[ERR] al guardar libro de bloqueo:", e);
			return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("{numeroCaja}/libros/{numeroLibro}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verLibro(
            @HeaderParam("token") String tokenUbicacion,
            @PathParam("numeroCaja") Integer numeroCaja,
            @PathParam("numeroLibro") Integer numeroLibro)
    {
    	logger.info ("verLibro[INI] token: {}", tokenUbicacion);
        logger.info ("verLibro[INI] numeroCaja: {}", numeroCaja);
        logger.info ("verLibro[INI] numeroLibro: {}", numeroLibro);
        
        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verLibro[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try 
        {
            LibroBloqueoTO pk = new LibroBloqueoTO();
            pk.setUbicacion(respUbic.getContenido().orElse(null));
            pk.setCaja(new CajaBloqueoTO());
            pk.getCaja().setNumero(numeroCaja);
            pk.setNumero(numeroLibro);
            Respuesta<LibroBloqueoInfoTO> r = FactoryBO.getLibroBloqueoBO().getLibro(pk);
            
            logger.info("verLibro[FIN] respuesta retornada: {}", r );
            return RespGenerica.of(r);
        } catch (Exception e) 
        {
			logger.error("verLibro[ERR] al buscar libro:", e);
			return RespGenerica.of(this.getClass(), e);
        }
    }

    @Deprecated
    @Path("{numeroCaja}/libros/{numeroLibro}/tags")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verTags()
    {
        return RespGenerica.exitosa();        
    }

    @Deprecated
    @Path("{numeroCaja}/libros/{numeroLibro}/energias")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verEnergias()
    {
        return RespGenerica.exitosa();        
    }

    @Deprecated
    @Path("{numeroCaja}/libros/{numeroLibro}/dotacion")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verDotacion()
    {
        return RespGenerica.exitosa();        
    }

    @Deprecated
    @Path("{numeroCaja}/libros/{numeroLibro}/responsables")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verResponsables()
    {
        return RespGenerica.exitosa();        
    }
}
