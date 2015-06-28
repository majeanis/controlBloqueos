package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.MensajeError;
import cl.cerrocolorado.recob.utils.Resultado;
import java.util.List;

/**
 *
 * @author mauricio.camara
 */
public interface CajaBloqueoBO
{
    public Respuesta<CajaBloqueoTO> guardar(CajaBloqueoTO cajaBloqueo) throws MensajeError;

    public Resultado eliminar(CajaBloqueoTO pkCaja) throws MensajeError;

    public CajaBloqueoTO get(CajaBloqueoTO pkCaja);

    public List<CajaBloqueoTO> get(UbicacionTO pkUbicacion);
    
    public List<CajaBloqueoTO> getAll(UbicacionTO pkUbicacion);
}
