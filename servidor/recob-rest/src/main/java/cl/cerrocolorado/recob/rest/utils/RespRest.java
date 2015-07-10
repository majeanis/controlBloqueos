package cl.cerrocolorado.recob.rest.utils;

import cl.cerrocolorado.recob.utils.Resultado;

public class RespRest<T> 
{
	private final RespHead encabezado;
	private final RespMensaje[] mensajes;
	private final T contenido;

	public RespRest(Resultado rtdo, T contenido)
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

	public T getContenido() {
		return contenido;
	}
}
