package cl.cerrocolorado.recob.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

@Path("testing")
public class Testing
{
    private static final Logger logger = LogManager.getLogger(Testing.class);

    @Context
    private HttpServletResponse httpResponse;
            
    @Path("objeto")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String verObjeto(@QueryParam("jsonCaja") String jsonCaja)
    {
        logger.info ("listarCajasBloqueo[INI] caja: {}", jsonCaja);
        httpResponse.addHeader("header", "{\"codigo\"=1,\"severidad\"=\"O\",\"texto\"=\"exitoso\"}");
        return jsonCaja;
    }
}
