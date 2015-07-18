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
import cl.cerrocolorado.recob.rest.utils.RespRest;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.EquipoTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.JsonUtils;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ResultadoProceso;

@Path("configuracion")
public class ConfiguracionService
{
    private static final Logger logger = LogManager.getLogger(ConfiguracionService.class);
    
    private static final UbicacionBO ubicacionBO;
    // private static final String token = "ec4b8544-1c73-11e5-9840-080027465435";

    static
    {
        ubicacionBO = FactoryBO.getUbicacionBO();
    }

    @Path("cajasBloqueo/listar")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespRest<List<CajaBloqueoTO>> listarCajasBloqueo(
    		@HeaderParam("token") String tokenUbicacion,
    		@QueryParam("vigencia") Boolean vigencia)
    {
        logger.info ("listarCajasBloqueo[INI] token: {}", tokenUbicacion);
        logger.info ("listarCajasBloqueo[INI] vigencia: {}", vigencia);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("listarCajasBloqueo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespFactory.getRespRest(respUbic.getResultado());
        }
        
        UbicacionTO ubicacion = respUbic.getContenido().get();
        List<CajaBloqueoTO> lista;
        
        if( vigencia == null || vigencia == true)
        {
        	lista = FactoryBO.getCajaBloqueoBO().getVigentes(ubicacion);
        } else
        {
        	lista = FactoryBO.getCajaBloqueoBO().getTodos(ubicacion);
        }
        
        logger.info ("listarCajasBloqueo[FIN] cajas retornadas: {}", lista );        
        return RespFactory.getRespRest(lista);
    }

    @Path("cajasBloqueo/guardar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public RespRest<CajaBloqueoTO> guardarCajaBloqueo(
    		@HeaderParam("token") String tokenUbicacion,
    		@QueryParam("caja") String jsonCaja)
    {
    	logger.info ("guardarCajaBloqueo[INI] token: {}", tokenUbicacion);
    	logger.info ("guardarCajaBloqueo[INI] jsonCaja: {}", jsonCaja);
        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("guardarCajaBloqueo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespFactory.getRespRest(respUbic.getResultado());
        }

    	Resultado rtdo = new ResultadoProceso();
        try 
        {
			CajaBloqueoTO caja = JsonUtils.fromJsonString(jsonCaja, CajaBloqueoTO.class);
			if( caja == null )
			{
				rtdo.addError(this.getClass(), "No se pudo parsear JSON con datos [caja]");
				logger.info ("guardarCajaBloqueo[FIN] salida con error: {}", rtdo);
				return RespFactory.getRespRest(rtdo);
			}

			// Asignamos la Ubicación a la que debe pertenecer la Caja
			caja.setUbicacion(respUbic.getContenido().get());
			Respuesta<CajaBloqueoTO> resp = FactoryBO.getCajaBloqueoBO().guardar(caja);

			logger.info ("guardarCajaBloqueo[FIN] resultado: {}", resp.getResultado());
	        logger.info ("guardarCajaBloqueo[FIN] caja registrada: {}", resp.getContenido());
			return RespFactory.getRespRest(resp.getResultado(), resp.getContenido());

        } catch (Exception e) 
        {
			rtdo.addException(this.getClass(), e);			
			logger.error("guardarCajaBloqueo[FIN] salida con error:", e);
			return RespFactory.getRespRest(this.getClass(), e);
        }
    }

    @Path("equipos/listar")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespRest<List<EquipoTO>> listarEquipos(
    		@HeaderParam("token") String tokenUbicacion,
    		@QueryParam("vigencia") Boolean vigencia)
    {
        logger.info ("listarEquipos[INI] token: {}", tokenUbicacion);
        logger.info ("listarEquipos[INI] vigencia: {}", vigencia);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("listarEquipos[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespFactory.getRespRest(respUbic.getResultado());
        }
        
        UbicacionTO ubicacion = respUbic.getContenido().get();
        List<EquipoTO> lista;
        
        if( vigencia == null || vigencia == true)
        {
        	lista = FactoryBO.getEquipoBO().getVigentes(ubicacion);
        } else
        {
        	lista = FactoryBO.getEquipoBO().getTodos(ubicacion);
        }
            
        logger.info ("listarEquipos[FIN] equipos retornadas: {}", lista );        
        return RespFactory.getRespRest(lista);
    }

    @Path("cajasBloqueo/ver")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespRest<CajaBloqueoTO> verCajaBloqueo(
    		@HeaderParam("token") String tokenUbicacion,
    		@QueryParam("id") Integer numeroCaja)
    {
        logger.info ("verCajaBloqueo[INI] token: {}", tokenUbicacion);
        logger.info ("verCajaBloqueo[INI] numeroCaja: {}", numeroCaja);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verCajaBloqueo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespFactory.getRespRest(respUbic.getResultado());
        }
        
        UbicacionTO ubicacion = respUbic.getContenido().get();
        CajaBloqueoTO caja = new CajaBloqueoTO();
        caja.setUbicacion(ubicacion);
        caja.setNumero(numeroCaja);
        Respuesta<CajaBloqueoTO> resp = FactoryBO.getCajaBloqueoBO().get(caja);

        logger.info ("verCajaBloqueo[FIN] respuesta retornado: {}", resp );
        return RespFactory.getRespRest(resp.getResultado(), resp.getContenido());
    }
    
    @Path("equipos/ver")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespRest<EquipoTO> verEquipo(
    		@HeaderParam("token") String tokenUbicacion,
    		@QueryParam("id") String codigoEquipo)
    {
        logger.info ("verEquipo[INI] token: {}", tokenUbicacion);
        logger.info ("verEquipo[INI] codigoEquipo: {}", codigoEquipo);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("verEquipos[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespFactory.getRespRest(respUbic.getResultado());
        }
        
        UbicacionTO ubicacion = respUbic.getContenido().get();
        EquipoTO equipo = new EquipoTO();
        equipo.setUbicacion(ubicacion);
        equipo.setCodigo(codigoEquipo);
        Respuesta<EquipoTO> resp = FactoryBO.getEquipoBO().get(equipo);

        logger.info ("verEquipo[FIN] respuesta retornado: {}", resp );
        return RespFactory.getRespRest(resp.getResultado(), resp.getContenido());
    }

    @Path("equipos/guardar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public RespRest<EquipoTO> guardarEquipo(
    		@HeaderParam("token") String tokenUbicacion,
    		@QueryParam("equipo") String jsonEquipo)
    {
    	logger.info ("guardarEquipo[INI] token: {}", tokenUbicacion);
    	logger.info ("guardarEquipo[INI] equipo: {}", jsonEquipo);
        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("guardarEquipo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespFactory.getRespRest(respUbic.getResultado());
        }

        Resultado rtdo = new ResultadoProceso();
        try 
        {
			EquipoTO equipo = JsonUtils.fromJsonString(jsonEquipo, EquipoTO.class);
			if( equipo == null )
			{
				rtdo.addError(this.getClass(), "No se pudo parsear JSON con datos [equipo]");
				logger.info ("guardarEquipo[FIN] salida con error: {}", rtdo);
				return RespFactory.getRespRest(rtdo);
			}

			// Asignamos la Ubicación a la que debe pertenecer el Equipos
            equipo.setUbicacion(respUbic.getContenido().get());
			Respuesta<EquipoTO> resp = FactoryBO.getEquipoBO().guardar(equipo);

			logger.info ("guardarEquipo[FIN] resultado: {}", resp.getResultado());
	        logger.info ("guardarEquipo[FIN] equipo registrado: {}", resp.getContenido());
			return RespFactory.getRespRest(resp.getResultado(), resp.getContenido());

        } catch (Exception e) 
        {
			rtdo.addException(this.getClass(), e);			
			logger.info ("guardarEquipo[FIN] al guardar equipo:", e);
			return RespFactory.getRespRest(this.getClass(), e);
        }
    }

}
