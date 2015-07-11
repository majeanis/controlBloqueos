package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.Resultado;
import java.util.List;

/**
 *
 * @author mauricio.camara
 */
public interface CajaBloqueoBO
{
    public Respuesta<CajaBloqueoTO> guardar(CajaBloqueoTO cajaBloqueo) throws Exception;

    public Resultado eliminar(CajaBloqueoTO pkCaja) throws Exception;

    public CajaBloqueoTO get(CajaBloqueoTO pkCaja);

    public List<CajaBloqueoTO> getVigentes(UbicacionTO pkUbicacion);
    
    public List<CajaBloqueoTO> getTodos(UbicacionTO pkUbicacion);
}
