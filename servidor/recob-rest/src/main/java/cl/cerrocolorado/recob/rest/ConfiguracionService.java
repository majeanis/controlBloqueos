package cl.cerrocolorado.recob.rest;

import cl.cerrocolorado.recob.bo.CajaBloqueoBO;
import cl.cerrocolorado.recob.bo.FactoryBO;
import cl.cerrocolorado.recob.bo.UbicacionBO;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.Respuesta;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("configuracion")
public class ConfiguracionService
{
    private static final UbicacionBO ubicacionBO;
    
    static
    {
        ubicacionBO = FactoryBO.getUbicacionBO();
    }

    @Path("saludo")
    @GET
    public String doSaludo()
    {
        return "Hola Rigby " + new Date();
    }
    
    @Path("cajasBloqueo/vigentes")
    @GET
    public List<CajaBloqueoTO> cajasBloqueoVigentes()
    {
        Respuesta<UbicacionTO> ubic = ubicacionBO.validarToken("ec4b8544-1c73-11e5-9840-080027465435");
        UbicacionTO ubicacion = ubic.getContenido().get();
        CajaBloqueoBO cajaBO = FactoryBO.getCajaBloqueoBO();

        return cajaBO.getVigentes(ubicacion);
    }
}
