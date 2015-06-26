package cl.cerrocolorado.recob.test;

import cl.cerrocolorado.recob.bo.CajaBloqueoBO;
import cl.cerrocolorado.recob.bo.FactoryBO;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.EstacionTO;
import cl.cerrocolorado.recob.to.UbicacionTO;

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

        caja.setNumero(1);
        caja.setNombre("CAJA N° 1");
        caja.setUbicacion( new UbicacionTO() );
        caja.setVigente(Boolean.TRUE);
        
        caja.getUbicacion().setId(2);
        caja.getUbicacion().setEstacion(new EstacionTO() );
        caja.getUbicacion().getEstacion().setId(1);
        
        bo.guardarCajaBloqueo(caja);
    }
}
