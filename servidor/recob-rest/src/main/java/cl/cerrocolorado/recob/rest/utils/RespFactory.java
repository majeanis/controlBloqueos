package cl.cerrocolorado.recob.rest.utils;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.cerrocolorado.recob.utils.JsonUtils;
import cl.cerrocolorado.recob.utils.Mensaje;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ResultadoProceso;
import cl.cerrocolorado.recob.utils.Severidad;

public class RespFactory {
	private static final Logger logger = LogManager.getLogger(RespFactory.class);
	
	public static String get(Resultado rtdo, Object contenido)
	{
		RespHead encabezado = new RespHead();
        RespMensaje[] mensajes = null;
        
        encabezado.setFecha(new Date());
        if(rtdo.esExitoso())
        {
            encabezado.setCodigo("1");
            encabezado.setSeveridad(Severidad.OK.getCodigo());
        } else
        {
            encabezado.setCodigo("0");
            encabezado.setSeveridad(rtdo.getSeveridad().getCodigo());
        }
        
        if( !rtdo.getMensajes().isEmpty() )
        {
            mensajes = new RespMensaje[ rtdo.getMensajes().size() ];
            for( int i=0; i < mensajes.length; i++)
            {
                Mensaje mensaje = rtdo.getMensajes().get(i);
                mensajes[i] = new RespMensaje();
                mensajes[i].setCodigo(mensaje.getCodigo());
                mensajes[i].setTexto(mensaje.getTexto());
                mensajes[i].setSeveridad(mensaje.getSeveridad().getCodigo());
            }
        }

        String jsonEncabezado = "\"encabezado\": " + JsonUtils.toJsonString(encabezado);
        String jsonMensajes = "";
        String jsonContenido = ""; 
        		
        if( mensajes != null )
        {
        	jsonMensajes = ", \"mensajes\": " + JsonUtils.toJsonString(mensajes);
        }
        
        if( contenido != null)
        {
        	jsonContenido = ", \"contenido\": " + JsonUtils.toJsonString(contenido);
        }

        logger.info ("get[FIN] jsonEncabezado: {}", jsonEncabezado );
        logger.info ("get[FIN] jsonMensajes: {}", jsonMensajes );
        logger.info ("get[FIN] jsonContenido: {}", jsonContenido );
        return String.join("", "{", jsonEncabezado,  jsonMensajes, jsonContenido, "}"); 
	}

	public static String get(Object contenido)
	{
		return get( new ResultadoProceso(), contenido);
	}
	
	public static String get(Resultado rtdo)
	{
		return get(rtdo,null);
	}
}
