package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.LibroBloqueoTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.Transaccional;
import java.util.Date;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author mauricio.camara
 */
public interface LibroBloqueoBO
{
	@Transaccional
    public Respuesta<LibroBloqueoTO> guardar(LibroBloqueoTO libro) throws Exception;

    public Respuesta<LibroBloqueoTO> get(LibroBloqueoTO pk);

    public Respuesta<List<LibroBloqueoTO>> getTodos(CajaBloqueoTO pk,
                                                    Optional<Boolean> vigencia,
                                                    Optional<Date> fechaLibro);

    public Respuesta<List<LibroBloqueoTO>> getVigentes(CajaBloqueoTO pk,
                                                       Optional<Date> fechaLibro);
}
