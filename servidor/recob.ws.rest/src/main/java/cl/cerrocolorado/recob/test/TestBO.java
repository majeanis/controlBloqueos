package cl.cerrocolorado.recob.test;

import cl.cerrocolorado.recob.bo.CajaBloqueoBO;
import cl.cerrocolorado.recob.bo.FactoryBO;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.EstacionTO;
import cl.cerrocolorado.recob.to.TrabajadorTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.MensajeError;
import cl.cerrocolorado.recob.utils.Rut;
import java.util.List;

/**
 *
 * @author mauricio.camara
 */
public class TestBO
{
    public static void testTrabajador()
    {
        TrabajadorTO trab = new TrabajadorTO();
        
        trab.setCargo("electrico");
        trab.setNombre("Mauricio Camara Molina");
        trab.setRut( Rut.valueOf("13005188-K") );
        trab.setTieneCursoBloqueo(Boolean.TRUE);
        trab.setVigente(Boolean.TRUE);
        
    }
    public static void main( String args[] ) throws MensajeError 
    {
        CajaBloqueoTO caja = new CajaBloqueoTO();
        CajaBloqueoBO bo = FactoryBO.getCajaBloqueoBO();

        caja.setNumero(2);
        caja.setNombre("CAJA N° " + caja.getNumero() );
        caja.setUbicacion( new UbicacionTO() );
        caja.setVigente(Boolean.TRUE);
        caja.getUbicacion().setId(2);
        caja.getUbicacion().setEstacion(new EstacionTO() );
        caja.getUbicacion().getEstacion().setId(1);
        
        bo.guardar(caja);
        
        List<CajaBloqueoTO> cajas = bo.get(caja.getUbicacion());
        System.out.println("retorno:" + cajas);
        
        
    }
}
