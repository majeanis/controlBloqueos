package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.UbicacionTO;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author mauricio.camara
 */
public interface CajaBloqueoBO extends BaseBO<CajaBloqueoTO>
{
    public Respuesta<List<CajaBloqueoTO>> getCajas(UbicacionTO pkUbicacion, 
                                                   Optional<Boolean> vigencia);
}
