package cl.cerrocolorado.recob.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.cerrocolorado.recob.bo.FactoryBO;
import cl.cerrocolorado.recob.bo.UbicacionBO;
import cl.cerrocolorado.recob.rest.utils.RespGenerica;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.EquipoTO;
import cl.cerrocolorado.recob.to.EquipoTagsTO;
import cl.cerrocolorado.recob.to.TagTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.JsonUtils;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.utils.Resultado;
import java.util.Arrays;
import javax.ws.rs.PathParam;

@Path("configuracion")
public class ConfiguracionService
{
    private static final Logger logger = LogManager.getLogger(ConfiguracionService.class);
    
    private static final UbicacionBO ubicacionBO;
    // private static final String token = "ec4b8544-1c73-11e5-9840-080027465435";

    private static final int VER_TODOS = 1;
    private static final int VER_VIGENTES = 2;
    private static final int VER_UNO = 3;
    
    static
    {
        ubicacionBO = FactoryBO.getUbicacionBO();
    }

    @Path("cajasBloqueo")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verCajasBloqueo(
    		@HeaderParam("token") String tokenUbicacion,
    		@QueryParam("accion") Integer accion,
    		@QueryParam("numero") Integer numeroCaja)
    {
        logger.info ("verCajasBloqueo[INI] token: {}", tokenUbicacion);
        logger.info ("verCajasBloqueo[INI] accion: {}", accion);
        logger.info ("verCajasBloqueo[INI] numeroCaja: {}", numeroCaja);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verCajasBloqueo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try
        {
            UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
            switch( accion )
            {
                case VER_TODOS:
                    Respuesta<List<CajaBloqueoTO>> r1 = FactoryBO.getCajaBloqueoBO().getTodos(ubicacion,null);
                    logger.info("verCajasBloqueo[FIN] retorno de todos los registros: {}", r1);
                    return RespGenerica.of(r1);

                case VER_VIGENTES:
                    Respuesta<List<CajaBloqueoTO>> r2 = FactoryBO.getCajaBloqueoBO().getVigentes(ubicacion);
                    logger.info("verCajasBloqueo[FIN] retorno de registros vigentes: {}", r2);
                    return RespGenerica.of(r2);

                case VER_UNO:
                    CajaBloqueoTO caja = new CajaBloqueoTO();
                    caja.setNumero(numeroCaja);
                    caja.setUbicacion(ubicacion);
                    Respuesta<CajaBloqueoTO> r3 = FactoryBO.getCajaBloqueoBO().get(caja);

                    logger.info("verCajasBloqueo[FIN] retorno de un registro: {}", r3);
                    return RespGenerica.of(r3);
            }
        } catch(Exception e)
        {
            logger.error("verCajasBloqueo[ERR] exception: ", e);
            return RespGenerica.of(this.getClass(), e);
        }

        logger.info("verCajasBloqueo[FIN] código de acción inválido: {}", accion);
        return null;
    }

    @Path("cajasBloqueo")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PUT
    public RespGenerica guardarCajaBloqueo(
    		@HeaderParam("token") String tokenUbicacion,
    		@QueryParam("caja") String jsonCaja)
    {
    	logger.info ("guardarCajaBloqueo[INI] token: {}", tokenUbicacion);
    	logger.info ("guardarCajaBloqueo[INI] jsonCaja: {}", jsonCaja);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("guardarCajaBloqueo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try 
        {
            UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
            CajaBloqueoTO caja = JsonUtils.fromJsonString(jsonCaja, CajaBloqueoTO.class);
            if( caja == null )
            {
                logger.info("guardarCajaBloqueo[FIN] no se pudo parsear el JSON: {}", jsonCaja);
                return null;
            }

            caja.setUbicacion(ubicacion);
            logger.debug("guardarCajaBloqueo[001] después de parsear el JSON: {}", caja);

            Respuesta<CajaBloqueoTO> r = FactoryBO.getCajaBloqueoBO().guardar(caja);

            logger.info("guardarCajaBloqueo[FIN] resultado registro caja: {}", r);
            return RespGenerica.of(r);
        } catch (Exception e) 
        {
			logger.error("guardarCajaBloqueo[ERR] al guardar caja de bloqueo:", e);
			return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("cajasBloqueo")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @DELETE
    public RespGenerica eliminarCajaBloqueo(
    		@HeaderParam("token") String tokenUbicacion,
    		@QueryParam("numero") Integer numeroCaja)
    {
        logger.info ("eliminarCajaBloqueo[INI] token: {}", tokenUbicacion);
        logger.info ("eliminarCajaBloqueo[INI] numeroCaja: {}", numeroCaja);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("eliminarCajaBloqueo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }
        
        try
        {
            UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
            CajaBloqueoTO caja = new CajaBloqueoTO();
            caja.setNumero(numeroCaja);
            caja.setUbicacion(ubicacion);

            Resultado r = FactoryBO.getCajaBloqueoBO().eliminar(caja);
            
            logger.info("eliminarCajaBloqueo[FIN] resultado de la eliminación: {}", r);
            return RespGenerica.of(r);
        } catch(Exception e)
        {
            logger.error("eliminarCajaBloqueo[ERR] al eliminar la caja:", e);
            return RespGenerica.of(this.getClass(), e);
        }
    }
    
    @Path("equipos")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verEquipos(
    		@HeaderParam("token") String tokenUbicacion,
            @QueryParam("accion") Integer accion,
    		@QueryParam("codigo") String codigoEquipo)
    {
        logger.info ("verEquipos[INI] token: {}", tokenUbicacion);
        logger.info ("verEquipos[INI] accion: {}", accion);
        logger.info ("verEquipos[INI] codigoEquipo: {}", codigoEquipo);
        
        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verEquipos[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try
        {
            UbicacionTO ubicacion = respUbic.getContenido().get();
            switch( accion )
            {
                case VER_TODOS:
                    Respuesta<List<EquipoTO>> r1 = FactoryBO.getEquipoBO().getTodos(ubicacion,null);
                    logger.info("verEquipos[FIN] retorno de todos los registros: {}", r1);
                    return RespGenerica.of(r1);

                case VER_VIGENTES:
                    Respuesta<List<EquipoTO>> r2 = FactoryBO.getEquipoBO().getVigentes(ubicacion);
                    logger.info("verEquipos[FIN] retorno de registros vigentes: {}", r2);
                    return RespGenerica.of(r2);

                case VER_UNO:
                    EquipoTO equipo = new EquipoTO();
                    equipo.setCodigo(codigoEquipo);
                    equipo.setUbicacion(ubicacion);
                    Respuesta<EquipoTagsTO> r3 = FactoryBO.getEquipoBO().get(equipo);
                    logger.info("verEquipos[FIN] retorno de un registro: {}", r3);
                    return RespGenerica.of(r3);
            }
        } catch(Exception e)
        {
            logger.error("verEquipos[ERR] exception: ", e);
            return RespGenerica.of(this.getClass(), e);
        }

        logger.info("verEquipos[FIN] código de acción inválido: {}", accion);
        return null;
    }
    
    @Path("equipos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PUT
    public RespGenerica guardarEquipo(
    		@HeaderParam("token") String tokenUbicacion,
    		@QueryParam("equipo") String jsonEquipo,
            @QueryParam("tags") String jsonTags)
    {
    	logger.info ("guardarEquipo[INI] token: {}", tokenUbicacion);
    	logger.info ("guardarEquipo[INI] equipo: {}", jsonEquipo);
        logger.info ("guardarEquipo[INI] tags: {}", jsonTags);
        
        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("guardarEquipo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try 
        {
            EquipoTagsTO equipo = JsonUtils.fromJsonString(jsonEquipo, EquipoTagsTO.class);
            if( equipo == null )
            {
                logger.info ("guardarEquipo[FIN] no se pudo parsear el JSON: {}", jsonEquipo);
                return null;
            }
            if( jsonTags != null )
            {
                TagTO[] tags = JsonUtils.fromJsonString(jsonTags, TagTO[].class);
                equipo.setTags(Arrays.asList(tags));
            }
            
            equipo.setUbicacion(respUbic.getContenido().orElse(null));
            logger.debug("guardarEquipo[001] después de parsear el JSON: {}", equipo);

            Respuesta<EquipoTagsTO> resp = FactoryBO.getEquipoBO().guardar(equipo);
	        logger.info ("guardarEquipo[FIN] equipo registrado: {}", resp);
			return RespGenerica.of(resp);
        } catch (Exception e) 
        {
			logger.error("guardarEquipo[ERR] al guardar equipo:", e);
			return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("equipos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @DELETE
    public RespGenerica eliminarEquipo(
    		@HeaderParam("token") String tokenUbicacion,
    		@QueryParam("codigo") String codigoEquipo)
    {
        logger.info ("eliminarEquipo[INI] token: {}", tokenUbicacion);
        logger.info ("eliminarEquipo[INI] codigoEquipo: {}", codigoEquipo);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("eliminarEquipo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }
        
        try
        {
            UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
            EquipoTO equipo = new EquipoTO();
            equipo.setCodigo(codigoEquipo);
            equipo.setUbicacion(ubicacion);
        
            Resultado r = FactoryBO.getEquipoBO().eliminar(equipo);
            
            logger.info("eliminarEquipo[FIN] resultado de la eliminación: {}", r);
            return RespGenerica.of(r);
        } catch(Exception e)
        {
            logger.error("eliminarEquipo[ERR] al eliminar equipo:", e);
            return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("equipos/{idEquipo}/tags")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verTags(
    		@HeaderParam("token") String tokenUbicacion,
            @PathParam("idEquipo") Integer idEquipo,
    		@QueryParam("accion") Integer accion,
    		@QueryParam("numero") Integer numeroTag)
    {
        logger.info ("verTags[INI] token: {}" , tokenUbicacion);
        logger.info ("verTags[INI] idEquipo: {}", idEquipo);
        logger.info ("verTags[INI] accion: {}", accion);
        logger.info ("verTags[INI] numeroTag: {}", numeroTag);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verTags[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        try
        {
            UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
            EquipoTO pkEquipo = new EquipoTO();
            pkEquipo.setUbicacion(ubicacion);
            pkEquipo.setId(idEquipo);

            switch( accion )
            {
                case VER_TODOS:
                    Respuesta<List<TagTO>> r1 = FactoryBO.getEquipoBO().getTagsTodos(pkEquipo,null);
                    logger.info("verTags[FIN] retorno de todos los registros: {}", r1);
                    return RespGenerica.of(r1);

                case VER_VIGENTES:
                    Respuesta<List<TagTO>> r2 = FactoryBO.getEquipoBO().getTagsVigentes(pkEquipo);
                    logger.info("verTags[FIN] retorno de registros vigentes: {}", r2);
                    return RespGenerica.of(r2);

                case VER_UNO:
                    TagTO tag = new TagTO();
                    tag.setEquipo(pkEquipo);
                    tag.setNumero(numeroTag);
                    Respuesta<TagTO> r3 = FactoryBO.getEquipoBO().getTag(tag);
                    logger.info("verTags[FIN] retorno de un registro: {}", r3);
                    return RespGenerica.of(r3);
            }
        } catch(Exception e)
        {
            logger.error("verTags[ERR] exception: ", e);
            return RespGenerica.of(this.getClass(), e);
        }

        logger.info("verTags[FIN] código de acción inválido: {}", accion);
        return null;
    }

}
