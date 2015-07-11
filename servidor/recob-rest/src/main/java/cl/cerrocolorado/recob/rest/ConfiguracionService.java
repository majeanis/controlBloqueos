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

    @Path("/cajasBloqueo/listar")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public RespRest<List<CajaBloqueoTO>> listarCajasBloqueo(
    		@HeaderParam("token") String tokenUbicacion,
    		@QueryParam("todos") Boolean todos)
    {
        logger.info ("listarCajasBloqueo[INI] token: {}", tokenUbicacion);
        logger.info ("listarCajasBloqueo[INI] todos: {}", todos);

        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("listarCajasBloqueo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespFactory.getRespRest(respUbic.getResultado());
        }
        
        UbicacionTO ubicacion = respUbic.getContenido().get();
        List<CajaBloqueoTO> lista;
        
        if( todos )
        {
        	lista = FactoryBO.getCajaBloqueoBO().getTodos(ubicacion);
        } else
        {
        	lista = FactoryBO.getCajaBloqueoBO().getVigentes(ubicacion);
        }
        
        logger.info ("listarCajasBloqueo[FIN] cajas retornadas: {}", lista );        
        return RespFactory.getRespRest(lista);
    }

    @Path("/cajasBloqueo/guardar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public RespRest<CajaBloqueoTO> guardarCajaBloqueo(
    		@HeaderParam("token") String tokenUbicacion,
    		@QueryParam("jsonCaja") String jsonCaja)
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

        } catch (Exception e) {
			rtdo.addException(this.getClass(), e);			
			logger.error("guardarCajaBloqueo[FIN] salida con error:", e);
			return RespFactory.getRespRest(this.getClass(), e);
        }
    }
}
