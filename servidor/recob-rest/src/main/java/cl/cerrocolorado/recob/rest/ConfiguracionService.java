package cl.cerrocolorado.recob.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.cerrocolorado.recob.bo.FactoryBO;
import cl.cerrocolorado.recob.bo.UbicacionBO;
import cl.cerrocolorado.recob.rest.utils.RespFactory;
import cl.cerrocolorado.recob.rest.utils.RespGenerica;
import cl.cerrocolorado.recob.rest.utils.RespRest;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.EquipoTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.JsonUtils;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ResultadoProceso;
import javax.ws.rs.DELETE;
import static javax.ws.rs.HttpMethod.PUT;
import javax.ws.rs.PUT;


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

    @Path("cajasBloqueo/ver")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verCajasBloqueo(
    		@HeaderParam("token") String tokenUbicacion,
    		@QueryParam("accion") Integer accion,
    		@QueryParam("id") String jsonPkCaja)
    {
        logger.info ("verCajasBloqueo[INI] token: {}", tokenUbicacion);
        logger.info ("verCajasBloqueo[INI] accion: {}", accion);
        logger.info ("verCajasBloqueo[INI] id: {}", jsonPkCaja);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verCajasBloqueo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }
        
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
                CajaBloqueoTO caja = JsonUtils.fromJsonString(jsonPkCaja, CajaBloqueoTO.class);
                if(caja!=null)
                {
                    caja.setUbicacion(ubicacion);
                    Respuesta<CajaBloqueoTO> r3 = FactoryBO.getCajaBloqueoBO().get(caja);
                    logger.info("verCajasBloqueo[FIN] retorno de un registro: {}", r3);
                    return RespGenerica.of(r3);
                }
        }

        logger.info("verCajasBloqueo[FIN] código de acción inválido: {}", accion);
        return null;
    }

    @Path("cajasBloqueo/guardar")
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

        UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
		CajaBloqueoTO caja = JsonUtils.fromJsonString(jsonCaja, CajaBloqueoTO.class);
        if( caja == null )
        {
            logger.info("guardarCajaBloqueo[FIN] no se pudo parsearl el JSON: {}", jsonCaja);
            return null;
        }

        try 
        {
            caja.setUbicacion(ubicacion);
            Respuesta<CajaBloqueoTO> r = FactoryBO.getCajaBloqueoBO().guardar(caja);

            logger.info("guardarCajaBloqueo[FIN] resultado registro caja: {}", r);
            return RespGenerica.of(r);
        } catch (Exception e) 
        {
			logger.error("guardarCajaBloqueo[ERR] al guardar caja de bloqueo:", e);
			return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("cajasBloqueo/eliminar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @DELETE
    public RespGenerica eliminarCajaBloqueo(
    		@HeaderParam("token") String tokenUbicacion,
    		@QueryParam("id") String jsonPkCaja)
    {
        logger.info ("eliminarCajaBloqueo[INI] token: {}", tokenUbicacion);
        logger.info ("eliminarCajaBloqueo[INI] id: {}", jsonPkCaja);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("eliminarCajaBloqueo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }
        
        UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
        CajaBloqueoTO caja = JsonUtils.fromJsonString(jsonPkCaja, CajaBloqueoTO.class);
        caja.setUbicacion(ubicacion);
        logger.debug("eliminarCajaBloqueo[001] después de parsear el JSON: {}", caja);
        
        try
        {
            Resultado r = FactoryBO.getCajaBloqueoBO().eliminar(caja);
            
            logger.info("eliminarCajaBloqueo[FIN] resultado de la eliminación: {}", r);
            return RespGenerica.of(r);
        } catch(Exception e)
        {
            logger.error("eliminarCajaBloqueo[ERR] al eliminar la caja:", e);
            return RespGenerica.of(this.getClass(), e);
        }
    }
    
    @Path("equipos/ver")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespGenerica verEquipos(
    		@HeaderParam("token") String tokenUbicacion,
            @QueryParam("accion") Integer accion,
    		@QueryParam("id") String jsonPkEquipo)
    {
        logger.info ("verEquipos[INI] token: {}", tokenUbicacion);
        logger.info ("verEquipos[INI] accion: {}", accion);
        logger.info ("verEquipos[INI] id: {}", jsonPkEquipo );
        
        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verEquipos[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }
        
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
                EquipoTO equipo = JsonUtils.fromJsonString(jsonPkEquipo, EquipoTO.class);
                if(equipo != null)
                {
                    equipo.setUbicacion(ubicacion);
                    Respuesta<EquipoTO> r3 = FactoryBO.getEquipoBO().get(equipo);
                    logger.info("verEquipos[FIN] retorno de un registro: {}", r3);
                    return RespGenerica.of(r3);
                }
        }

        logger.info("verEquipos[FIN] código de acción inválido: {}", accion);
        return null;
    }
    
    @Path("equipos/guardar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PUT
    public RespGenerica guardarEquipo(
    		@HeaderParam("token") String tokenUbicacion,
    		@QueryParam("equipo") String jsonEquipo)
    {
    	logger.info ("guardarEquipo[INI] token: {}", tokenUbicacion);
    	logger.info ("guardarEquipo[INI] equipo: {}", jsonEquipo);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("guardarEquipo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }

        EquipoTO equipo = JsonUtils.fromJsonString(jsonEquipo, EquipoTO.class);
        if( equipo == null )
        {
            logger.info ("guardarEquipo[FIN] no se pudo parsear el JSON: {}", jsonEquipo);
            return null;
        }

        equipo.setUbicacion(respUbic.getContenido().orElse(null));
        logger.debug("guardarEquipo[001] después de parsear el JSON: {}", equipo);

        try 
        {
			Respuesta<EquipoTO> resp = FactoryBO.getEquipoBO().guardar(equipo);
	        logger.info ("guardarEquipo[FIN] equipo registrado: {}", resp);
			return RespGenerica.of(resp);
        } catch (Exception e) 
        {
			logger.error("guardarEquipo[ERR] al guardar equipo:", e);
			return RespGenerica.of(this.getClass(), e);
        }
    }

    @Path("equipos/eliminar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @DELETE
    public RespGenerica eliminarEquipo(
    		@HeaderParam("token") String tokenUbicacion,
    		@QueryParam("id") String jsonPkEquipo)
    {
        logger.info ("eliminarEquipo[INI] token: {}", tokenUbicacion);
        logger.info ("eliminarEquipo[INI] id: {}", jsonPkEquipo);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("eliminarEquipo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespGenerica.of(respUbic);
        }
        
        UbicacionTO ubicacion = respUbic.getContenido().orElse(null);
        EquipoTO equipo = JsonUtils.fromJsonString(jsonPkEquipo, EquipoTO.class);
        equipo.setUbicacion(ubicacion);
        logger.debug("eliminarEquipo[001] después de parsear el JSON: {}", equipo);
        
        try
        {
            Resultado r = FactoryBO.getEquipoBO().eliminar(equipo);
            
            logger.info("eliminarEquipo[FIN] resultado de la eliminación: {}", r);
            return RespGenerica.of(r);
        } catch(Exception e)
        {
            logger.error("eliminarEquipo[ERR] al eliminar equipo:", e);
            return RespGenerica.of(this.getClass(), e);
        }
    }
}
