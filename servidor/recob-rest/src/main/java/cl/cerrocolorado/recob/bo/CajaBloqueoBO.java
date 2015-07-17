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
	@Transaccional
    public Respuesta<CajaBloqueoTO> guardar(CajaBloqueoTO cajaBloqueo) throws Exception;

	@Transaccional
    public Resultado eliminar(CajaBloqueoTO pkCaja) throws Exception;

    public Respuesta<CajaBloqueoTO> get(CajaBloqueoTO pkCaja);

    public List<CajaBloqueoTO> getVigentes(UbicacionTO pkUbicacion);
    
    public List<CajaBloqueoTO> getTodos(UbicacionTO pkUbicacion);
}
