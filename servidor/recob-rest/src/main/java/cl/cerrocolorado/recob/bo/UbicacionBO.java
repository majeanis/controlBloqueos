package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.Respuesta;

/**
 * @author mauricio.camara
 */
public interface UbicacionBO
{
    public UbicacionTO get(String tokenUbicacion);
    public Respuesta<UbicacionTO> validarToken(String tokenUbicacion);
}
