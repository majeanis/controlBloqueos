package cl.cerrocolorado.recob.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import cl.cerrocolorado.recob.bo.FactoryBO;
import cl.cerrocolorado.recob.to.EmpresaTO;
import cl.cerrocolorado.recob.utils.JsonUtils;
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

    public static void testJson()
    {
    	Date date = new Date();
    	LocalDate localDate = LocalDate.now();
    	LocalDateTime localDateTime = LocalDateTime.now();
    	
    	String[] json = new String[3];
    	json[0] = JsonUtils.toJsonString(date);
    	json[1] = JsonUtils.toJsonString(localDate);
    	json[2] = JsonUtils.toJsonString(localDateTime);
    	
    	System.out.println( date + ":" + json[0] );
    	System.out.println( localDate + ":" + json[1] );
    	System.out.println( localDateTime + ":" + json[2] );
    }

    public static void main(String[] args)
    {
        testJson();
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
