package cl.cerrocolorado.recob.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import cl.cerrocolorado.recob.bo.utils.FactoryBO;
import cl.cerrocolorado.recob.to.EmpresaTO;
import cl.cerrocolorado.recob.utils.JsonUtils;
import cl.cerrocolorado.recob.utils.Rut;
import static cl.cerrocolorado.recob.utils.ToStringUtils.toString;
import cl.cerrocolorado.recob.utils.ValidUtils;
import com.google.common.base.Optional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TestRecob
{
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

    public static void testRest()
    {
  	  try {
			URL url = new URL("http://localhost:5080/recobWS/rest/configuracion/cajasBloqueo/listar?todos=false");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println("{"+output+"}");
			}

			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }

    public static String toString(String textoBase, final Object... valores)
    {
        if (textoBase == null)
            return "";

        String textoFormateado = textoBase.trim();

        if (valores == null)
            return textoFormateado;

        for (int i = 0; i < valores.length; i++)
        {
            String values = valores[i].toString();  String.valueOf(valores[i]);
            textoFormateado = textoFormateado.replace("#{" + (i + 1) + "}", values);
        }

        /*
         * Una vez puestos los valores en el texto base, se procede a eliminar
         * todas las posibles marcas que no fueron reemplazadas, producto que no
         * se dieron todos los valores necesarios, de tal manera que en el
         * String resultante no queden cadenas del tipo "#{n}".
         */
        textoFormateado = textoFormateado.replaceAll("\\#\\{[1-9]+\\}", "");
        textoFormateado = textoFormateado.replace("  ", " ");

        return textoFormateado;
    }

    public static void main(String[] args)
    {
//        testRest();
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
