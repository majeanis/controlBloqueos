package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.po.CajaBloqueoPO;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
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
}
