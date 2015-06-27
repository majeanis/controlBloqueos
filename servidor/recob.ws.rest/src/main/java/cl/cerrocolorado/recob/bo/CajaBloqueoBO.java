package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.MensajeError;
import java.util.List;

/**
 *
 * @author mauricio.camara
 */
public interface CajaBloqueoBO
{
    public Respuesta<CajaBloqueoTO> guardar(CajaBloqueoTO cajaBloqueo) throws MensajeError;

    public Respuesta<CajaBloqueoTO> eliminar(CajaBloqueoTO key) throws MensajeError;

    public CajaBloqueoTO get(CajaBloqueoTO key);

    public List<CajaBloqueoTO> get(UbicacionTO ubicacion);
}
