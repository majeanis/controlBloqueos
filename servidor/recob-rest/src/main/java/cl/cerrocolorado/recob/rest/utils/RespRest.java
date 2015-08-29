package cl.cerrocolorado.recob.rest.utils;

import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ToStringUtils;

public class RespRest<T> 
{
	private final RespHead head;
	private final T body;

	public RespRest(Resultado rtdo, T body)
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
}
