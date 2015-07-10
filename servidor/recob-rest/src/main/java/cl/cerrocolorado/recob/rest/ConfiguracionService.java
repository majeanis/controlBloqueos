package cl.cerrocolorado.recob.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.cerrocolorado.recob.bo.FactoryBO;
import cl.cerrocolorado.recob.bo.UbicacionBO;
import cl.cerrocolorado.recob.rest.utils.RespFactory;
import cl.cerrocolorado.recob.rest.utils.RespRest;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.Respuesta;

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
    
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{token}/cajasBloqueo")
    @GET
    public RespRest<List<CajaBloqueoTO>> cajasBloqueoVigentes(@PathParam("token") String tokenUbicacion)
    {
        logger.info ("cajasBloqueoVigentes[INI] token: {}", tokenUbicacion);
        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("cajasBloqueoVigentes[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespFactory.getRespRest(respUbic.getResultado());
        }
        
        UbicacionTO ubicacion = respUbic.getContenido().get();
        List<CajaBloqueoTO> lista = FactoryBO.getCajaBloqueoBO().getVigentes(ubicacion);
        
        logger.info ("cajasBloqueoVigentes[FIN] cajas retornadas: {}", lista );        
        return RespFactory.getRespRest(lista);
    }

    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{token}/cajasBloqueo/todos")
    @GET
    public RespRest<List<CajaBloqueoTO>> cajasBloqueo(@PathParam("token") String tokenUbicacion)
    {
    	logger.info ("cajasBloqueo[INI] token: {}", tokenUbicacion);
        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("cajasBloqueo[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return RespFactory.getRespRest(respUbic.getResultado());
        }

        UbicacionTO ubicacion = respUbic.getContenido().get();
        List<CajaBloqueoTO> lista = FactoryBO.getCajaBloqueoBO().getTodos(ubicacion);

        logger.info ("cajasBloqueo[FIN] cajas retornadas: {}", lista );
        return RespFactory.getRespRest(lista);
    }
}
