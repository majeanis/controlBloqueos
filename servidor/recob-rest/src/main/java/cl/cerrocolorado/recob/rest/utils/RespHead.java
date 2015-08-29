package cl.cerrocolorado.recob.rest.utils;

import cl.cerrocolorado.recob.utils.Resultado;
import java.util.Date;

import cl.cerrocolorado.recob.utils.ToStringUtils;

public class RespHead
{
    private final Date fecha;
    private final Boolean isOK;
	private final RespMensaje[] mensajes;
    
    public RespHead(Resultado rtdo)
    {
        fecha = new Date();
        if( rtdo == null )
        {
            isOK = true;
            mensajes = new RespMensaje[0];
        }
        else
        {
            isOK = rtdo.esExitoso();
            mensajes = new RespMensaje[ rtdo.getMensajes().size() ];
            for( int i = 0; i < rtdo.getMensajes().size(); i++)
            {
                mensajes[i] = new RespMensaje();
                mensajes[i].setCodigo( rtdo.getMensajes().get(i).getCodigo());
                mensajes[i].setTexto( rtdo.getMensajes().get(i).getTexto());
                mensajes[i].setSeveridad( rtdo.getMensajes().get(i).getSeveridad().name().substring(0,1));
            }
        }
    }
    public Date getFecha()
    {
        return fecha;
    }
    public Boolean isOK()
    {
        return isOK;
    }
    
    @Override
    public String toString()
    {
        return ToStringUtils.toString(this);
    }
}
