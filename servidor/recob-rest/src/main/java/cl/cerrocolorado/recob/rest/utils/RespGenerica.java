package cl.cerrocolorado.recob.rest.utils;

import cl.cerrocolorado.recob.utils.Mensaje;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ResultadoProceso;

public class RespGenerica
{
	private final RespHead encabezado;
	private final RespMensaje[] mensajes;
	private final Object contenido;

	private RespGenerica(Resultado rtdo, Object contenido)
	{
		this.encabezado = RespFactory.getRespHead(rtdo);
		this.mensajes = RespFactory.getRespMensajes(rtdo);
		this.contenido = contenido;
	}

	public RespHead getEncabezado() {
		return encabezado;
	}

	public RespMensaje[] getMensajes() {
		return mensajes;
	}

	public Object getContenido() {
		return contenido;
	}

    public static RespGenerica of(Resultado rtdo, Object contenido)
    {
        return new RespGenerica(rtdo,contenido);
    }
    
    public static <T> RespGenerica of(Respuesta<T> respBO)
    {
        if( respBO == null )
        {
            return new RespGenerica(null,null);
        }
        
        return new RespGenerica(respBO.getResultado(), respBO.getContenido().orElse(null));
    }
    
    public static RespGenerica of(Resultado rtdo)
    {
        return new RespGenerica(rtdo,null);
    }
    
    public static RespGenerica of(Object contenido)
    {
        return new RespGenerica(null, contenido);
    }
    	
	public static RespGenerica of(Class<?> clazz, Exception exception)
	{
    	Resultado rtdo = new ResultadoProceso();
		rtdo.addException(clazz, exception);
		return new RespGenerica(rtdo,null);
	}

  	public static RespGenerica of(Mensaje mensaje)
	{
    	Resultado rtdo = new ResultadoProceso();
		rtdo.addMensaje(mensaje);
		return new RespGenerica(rtdo,null);
	}

    public static RespGenerica exitosa()
    {
		return new RespGenerica(new ResultadoProceso(),null);
    }
}
