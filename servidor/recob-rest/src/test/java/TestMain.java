
import cl.cerrocolorado.recob.to.entidades.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.entidades.ResponsableTO;
import cl.cerrocolorado.recob.to.entidades.CandadoTO;
import cl.cerrocolorado.recob.to.entidades.PersonaTO;
import cl.cerrocolorado.recob.utils.ObjetoTO;
import cl.cerrocolorado.recob.utils.Rut;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

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
   public static void main(String[] args) throws IOException
    {
        String j2 = "{\"rut\":\"13005188-K\",\"id\":0,\"numero\":3,\"nombre\":\"CAJA N° 3\",\"vigente\":true}";
        ObjectMapper mapper = new ObjectMapper();
        CajaBloqueoTO c = mapper.readValue(j2, CajaBloqueoTO.class);
        
        System.out.println( c.getClass().isAssignableFrom(ObjetoTO.class) );
        System.out.println( ObjetoTO.class.isAssignableFrom(c.getClass()) );
    }
}
