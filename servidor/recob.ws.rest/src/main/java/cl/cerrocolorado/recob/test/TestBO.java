package cl.cerrocolorado.recob.test;

import cl.cerrocolorado.recob.bo.CajaBloqueoBO;
import cl.cerrocolorado.recob.bo.FactoryBO;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import java.util.List;

/**
 *
 * @author mauricio.camara
 */
public class TestBO
{
    public static void main( String args[] ) 
    {
        CajaBloqueoTO caja = new CajaBloqueoTO();
        CajaBloqueoBO bo = FactoryBO.getCajaBloqueoBO();

//        caja.setNumero(2);
//        caja.setNombre("CAJA N° " + caja.getNumero() );
        caja.setUbicacion( new UbicacionTO() );
//        caja.setVigente(Boolean.TRUE);
//        
        caja.getUbicacion().setId(2);
//        caja.getUbicacion().setEstacion(new EstacionTO() );
//        caja.getUbicacion().getEstacion().setId(1);
//        
//        bo.guardarCajaBloqueo(caja);
        
        List<CajaBloqueoTO> cajas = bo.getCajasBloqueos(caja.getUbicacion().getId());
        System.out.println("retorno:" + cajas);
    }
}
