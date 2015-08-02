package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.Transaccional;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author mauricio.camara
 */
public interface CajaBloqueoBO
{
	@Transaccional
    public Respuesta<CajaBloqueoTO> guardar(CajaBloqueoTO cajaBloqueo) throws Exception;

	@Transaccional
    public Respuesta<CajaBloqueoTO> eliminar(CajaBloqueoTO pkCaja) throws Exception;

    public Respuesta<CajaBloqueoTO> get(CajaBloqueoTO pkCaja);

    public Respuesta<List<CajaBloqueoTO>> getTodos(UbicacionTO pkUbicacion, 
                                                   Optional<Boolean> vigencia);

    public Respuesta<List<CajaBloqueoTO>> getVigentes(UbicacionTO pkUbicacion);
}
