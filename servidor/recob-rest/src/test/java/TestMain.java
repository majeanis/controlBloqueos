
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.CandadoTO;
import cl.cerrocolorado.recob.to.PersonaTO;
import cl.cerrocolorado.recob.utils.Rut;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mauricio.camara
 */
public class TestMain
{
    public static void main(String[] args)
    {
        CajaBloqueoTO caja = null;
        CandadoTO candado = null;
        PersonaTO persona = new PersonaTO();
        persona.setRut(Rut.valueOf("13005188-K"));
        caja = new CajaBloqueoTO();
//        
//        caja.setId(1);
//        System.out.println(ValidUtils.isPropertyBlank(persona, "rut"));
        persona = null;
        if( candado == null || candado.getPersona() == null || Rut.isBlank(candado.getPersona().getRut()) )
        {
            System.out.println( "works" );
        }
    }
}
