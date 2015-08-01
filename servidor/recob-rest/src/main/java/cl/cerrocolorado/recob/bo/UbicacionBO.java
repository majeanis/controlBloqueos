package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.to.FuncionBloqueoTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.Respuesta;
import java.util.List;
import java.util.Optional;

/**
 * @author mauricio.camara
 */
public interface UbicacionBO
{
    public UbicacionTO get(String tokenUbicacion);
    public Respuesta<UbicacionTO> validarToken(String tokenUbicacion);
    public Respuesta<List<FuncionBloqueoTO>> getFunciones(Optional<Boolean> vigencia);
}
