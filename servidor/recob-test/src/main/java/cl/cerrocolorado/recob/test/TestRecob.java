package cl.cerrocolorado.recob.test;

import cl.cerrocolorado.recob.bo.FactoryBO;
import cl.cerrocolorado.recob.to.EmpresaTO;
import cl.cerrocolorado.recob.utils.Rut;

public class TestRecob
{
    public static void testEmpresa()
    {
        EmpresaTO empr = new EmpresaTO();

        empr.setRut( Rut.valueOf("13005188-K") );
        empr.setNombre("Mauricio Camara Molina");
        empr.setVigente(Boolean.TRUE);
        FactoryBO.getEmpresaBO().guardar(empr);
        System.out.println( "Registro Guardado:" + empr );

        empr = new EmpresaTO();
        empr.setRut( Rut.valueOf("13640210-2") );
        empr.setNombre("Jéssica Godoy Cossio");
        empr.setVigente(Boolean.TRUE);
        FactoryBO.getEmpresaBO().guardar(empr);
        System.out.println( "Registro Guardado:" + empr );

        System.out.println( FactoryBO.getEmpresaBO().getTodos() );
    }

    public static void main(String[] args)
    {
        testEmpresa();
//      
//      CajaBloqueoTO caja = new CajaBloqueoTO();
//      CajaBloqueoBO bo = FactoryBO.getCajaBloqueoBO();
//
//      caja.setNumero(2);
//      caja.setNombre("CAJA N° " + caja.getNumero() );
//      caja.setUbicacion( new UbicacionTO() );
//      caja.setVigente(Boolean.TRUE);
//      caja.getUbicacion().setId(2);
//      caja.getUbicacion().setEstacion(new EstacionTO() );
//      caja.getUbicacion().getEstacion().setId(1);
//      
//      bo.guardar(caja);
//      
//      List<CajaBloqueoTO> cajas = bo.getVigentes(caja.getUbicacion());
//      System.out.println("retorno:" + cajas);
    }

}
