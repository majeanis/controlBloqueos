package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import java.util.List;

/**
 *
 * @author mauricio.camara
 */
public interface CajaBloqueoBO
{
    public CajaBloqueoTO guardarCajaBloqueo(CajaBloqueoTO cajaBloqueo);

    public CajaBloqueoTO getCajaBloqueo(int idCaja);

    public CajaBloqueoTO getCajaBloqueo(int idUbicacion, int numeroCaja);

    public List<CajaBloqueoTO> getCajasBloqueos(int idUbicacion);
}
