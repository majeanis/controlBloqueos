package cl.cerrocolorado.recob.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.cerrocolorado.recob.bo.CajaBloqueoBO;
import cl.cerrocolorado.recob.bo.FactoryBO;
import cl.cerrocolorado.recob.bo.UbicacionBO;
import cl.cerrocolorado.recob.rest.utils.BaseRespuesta;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.Respuesta;

@Path("configuracion")
public class ConfiguracionService
{
    private static final Logger logger = LogManager.getLogger(ConfiguracionService.class);
    
    private static final UbicacionBO ubicacionBO;
    private static final String token = "ec4b8544-1c73-11e5-9840-080027465435";

    static
    {
    	ubicacionBO = null;
//        ubicacionBO = FactoryBO.getUbicacionBO();
    }
    
    @Path("/probando")
    @GET
    public String saludo()
    {
    	return "I'm Live!";
    }
    
    @Path("/{token}/cajasBloqueo")
//    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public BaseRespuesta<CajaBloqueoTO[]> cajasBloqueoVigentes(@PathParam("token") String tokenUbicacion)
    {
        logger.info ("cajasBloqueoVigentes[INI] token: {}", tokenUbicacion);
        Respuesta<UbicacionTO> respUbic = ubicacionBO.validarToken(tokenUbicacion);
        if( !respUbic.getResultado().esExitoso() )
        {
            logger.info ("cajasBloqueoVigentes[FIN] token de ubicación inválido: {}", tokenUbicacion);
            return BaseRespuesta.of(respUbic.getResultado());
        }
        
        UbicacionTO ubicacion = respUbic.getContenido().get();
        List<CajaBloqueoTO> lista = FactoryBO.getCajaBloqueoBO().getVigentes(ubicacion);
        
        logger.info ("cajasBloqueoVigentes[FIN] cajas retornadas: {}", lista );        
        return BaseRespuesta.of(lista.toArray(new CajaBloqueoTO[0]));
    }
    
    @Path("cajasBloqueo/todos")
    @GET
    public List<CajaBloqueoTO> cajasBloqueo()
    {
        Respuesta<UbicacionTO> ubic = ubicacionBO.validarToken(token);
        UbicacionTO ubicacion = ubic.getContenido().get();
        CajaBloqueoBO cajaBO = FactoryBO.getCajaBloqueoBO();

        return cajaBO.getTodos(ubicacion);
    }
}
