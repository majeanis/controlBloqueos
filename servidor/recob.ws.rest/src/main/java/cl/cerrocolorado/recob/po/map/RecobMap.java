package cl.cerrocolorado.recob.po.map;

import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mauricio.camara
 */
public interface RecobMap
{
    /**
     * Manipulación de Cajas de Bloqueo
     **/ 
    public void insertCajaBloqueo(CajaBloqueoTO caja);
    public void updateCajaBloqueo(CajaBloqueoTO caja);
    public List<CajaBloqueoTO> selectCajasBloqueos( Map<String,Object> params );
    /**/
}
