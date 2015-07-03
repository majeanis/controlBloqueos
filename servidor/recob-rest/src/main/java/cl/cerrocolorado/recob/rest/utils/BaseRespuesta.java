package cl.cerrocolorado.recob.rest.utils;

import java.util.Date;

import cl.cerrocolorado.recob.utils.Mensaje;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ResultadoProceso;
import cl.cerrocolorado.recob.utils.Severidad;

public class BaseRespuesta<T>
{
    private RespHead encabezado;
    private RespMensaje[] mensajes;
    private T contenido;

    public void setEncabezado(RespHead encabezado)
    {
        this.encabezado = encabezado;
    }
    public RespHead getEncabezado()
    {
        return encabezado;
    }
    public void setMensajes(RespMensaje[] mensajes)
    {
        this.mensajes = mensajes;
    }
    public RespMensaje[] getMensajes()
    {
        return mensajes;
    }
    public void setContenido(T contenido)
    {
        this.contenido = contenido;
    }
    public T getContenido()
    {
        return contenido;
    }

    public static <T> BaseRespuesta<T> of(Resultado rtdo, T contenido)
    {
        BaseRespuesta<T> nuevo = new BaseRespuesta<T>();
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

        nuevo.setEncabezado(encabezado);
        nuevo.setMensajes(mensajes);
        nuevo.setContenido(contenido);
        return nuevo;
    }

    public static <T> BaseRespuesta<T> of(Resultado rtdo)
    {
        return of(rtdo, (T) null);
    }
    public static <T> BaseRespuesta<T> of(T contenido)
    {
        return of(new ResultadoProceso(), contenido);
    }
}
