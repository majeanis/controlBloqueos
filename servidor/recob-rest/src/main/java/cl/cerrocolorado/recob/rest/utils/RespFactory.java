package cl.cerrocolorado.recob.rest.utils;

import java.util.Date;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.cerrocolorado.recob.utils.JsonUtils;
import cl.cerrocolorado.recob.utils.Mensaje;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ResultadoProceso;
import cl.cerrocolorado.recob.utils.Severidad;

public class RespFactory 
{
	private static final Logger logger = LogManager.getLogger(RespFactory.class);

    public static RespHead getRespHead(Resultado rtdo)
    {
		RespHead encabezado = new RespHead();
        
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
        
        return encabezado;
    }
    
    public static RespMensaje[] getRespMensajes(Resultado rtdo)
    {
    	if( rtdo == null )
    		return null;
    	
    	if( rtdo.getMensajes().isEmpty() )
    		return null;

    	RespMensaje[] mensajes = new RespMensaje[ rtdo.getMensajes().size() ];
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
    	
    	return mensajes;
    }

	public static String getJson(Resultado rtdo, Object contenido)
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

	public static String getJson(Object contenido)
	{
		return getJson( new ResultadoProceso(), contenido);
	}
	
	public static String getJson(Resultado rtdo)
	{
		return getJson(rtdo,null);
	}
	
	public static <T> RespRest<T> getRespRest(Resultado rtdo, T contenido)
	{
		return new RespRest<T>(rtdo,contenido);
	}

	public static <T> RespRest<T> getRespRest(Resultado rtdo, Optional<T> contenido)
	{
		if( contenido.isPresent())
		{
			return new RespRest<T>(rtdo,contenido.get());			
		} else
		{
			return new RespRest<T>(rtdo,null);
		}
	}

	public static <T> RespRest<T> getRespRest(Resultado rtdo)
	{
		return new RespRest<T>(rtdo,null);
	}

	public static <T> RespRest<T> getRespRest(T contenido)
	{
		return new RespRest<T>(new ResultadoProceso(), contenido);
	}
	
	public static <T> RespRest<T> getRespRest(Class<?> clazz, Exception exception)
	{
    	Resultado rtdo = new ResultadoProceso();
		rtdo.addException(clazz, exception);
		return RespFactory.getRespRest(rtdo);
	}
}
