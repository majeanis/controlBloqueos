package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.po.CajaBloqueoPO;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author mauricio.camara
 */
@Scope("singleton")
@Service("cajaBloqueoBO")
public class CajaBloqueoBean implements CajaBloqueoBO
{
    @Autowired
    private CajaBloqueoPO cajaPO;
    
    @Transaccional
    @Override
    public CajaBloqueoTO guardarCajaBloqueo(CajaBloqueoTO cajaBloqueo)
    {
        cajaPO.guardarCajaBloqueo(cajaBloqueo);
        return cajaBloqueo;
    }

    @Override
    public CajaBloqueoTO getCajaBloqueo(int idCaja)
    {
        CajaBloqueoTO caja = cajaPO.getCajaBloqueo(idCaja);
        return caja;
    }

    @Override
    public CajaBloqueoTO getCajaBloqueo(int idUbicacion, int numeroCaja)
    {
        CajaBloqueoTO caja = cajaPO.getCajaBloqueo(idUbicacion, numeroCaja);
        return caja;
    }

    @Override
    public List<CajaBloqueoTO> getCajasBloqueos(int idUbicacion)
    {
        List<CajaBloqueoTO> cajas = cajaPO.getCajasBloqueos(idUbicacion);
        return cajas;
    }
}
