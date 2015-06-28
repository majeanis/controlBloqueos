package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.Transaccional;
import java.util.List;

/**
 *
 * @author mauricio.camara
 */
public interface CajaBloqueoBO
{
    public Respuesta<CajaBloqueoTO> guardar(CajaBloqueoTO cajaBloqueo);

    public Resultado eliminar(CajaBloqueoTO pkCaja);

    public CajaBloqueoTO get(CajaBloqueoTO pkCaja);

    public List<CajaBloqueoTO> getVigentes(UbicacionTO pkUbicacion);
    
    public List<CajaBloqueoTO> getTodos(UbicacionTO pkUbicacion);
}
