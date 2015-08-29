package cl.cerrocolorado.recob.rest.utils;

import cl.cerrocolorado.recob.utils.Mensaje;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ResultadoProceso;
import cl.cerrocolorado.recob.utils.ToStringUtils;

public class RespRest<T> 
{
	private final RespHead head;
	private final T body;

	private RespRest(Resultado rtdo, T body)
	{
		this.head = new RespHead( rtdo ); 
		this.body = body;
	}

	public RespHead getHead() {
		return head;
	}

	public T getBody() {
		return body;
	}
    
    @Override
    public String toString()
    {
        return ToStringUtils.toString(this);
    }

    public static <T> RespRest of(Resultado rtdo, T body)
    {
        return new RespRest<>(rtdo,body);
    }
    
    public static <T> RespRest of(Respuesta<T> respBO)
    {
        if( respBO == null )
        {
            return new RespRest<>(null,null);
        }
        
        return new RespRest<>(respBO.getResultado(), respBO.getContenido().orElse(null));
    }
    
    public static <T> RespRest of(Resultado rtdo)
    {
        return new RespRest(rtdo,null);
    }
    
    public static <T> RespRest of(T body)
    {
        return new RespRest(null, body);
    }
    	
	public static <T> RespRest of(Class<?> clazz, Exception exception)
	{
    	Resultado rtdo = new ResultadoProceso();
		rtdo.addException(clazz, exception);
		return new RespRest<>(rtdo,null);
	}

  	public static <T> RespRest of(Mensaje mensaje)
	{
    	Resultado rtdo = new ResultadoProceso();
		rtdo.addMensaje(mensaje);
		return new RespRest<>(rtdo,null);
	}

    public static <T> RespRest exitosa()
    {
		return new RespRest(new ResultadoProceso(),null);
    }
}
