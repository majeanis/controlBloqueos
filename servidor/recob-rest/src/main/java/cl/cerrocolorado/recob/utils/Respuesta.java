package cl.cerrocolorado.recob.utils;

import java.io.Serializable;
import java.util.Optional;

public class Respuesta<T> implements Serializable
{
    private static final long serialVersionUID = 1L;

    private final Resultado   resultado;
    private final Optional<T> contenido;

    private Respuesta(Resultado resultado, T contenido)
    {
        if (resultado != null)
        {
            this.resultado = resultado;
        } else
        {
           this.resultado = new ResultadoProceso();
        }

        this.contenido = Optional.ofNullable(contenido);
    }

    public Resultado getResultado()
    {
        return resultado;
    }

    public Optional<T> getContenido()
    {
        return contenido;
    }

    @Override
    public String toString()
    {
        return ToStringUtils.toString(this);
    }
    
    public static <T> Respuesta<T> of(Resultado resultado, T contenido)
    {
        return new Respuesta<>(resultado, contenido);
    }
    
    public static <T> Respuesta<T> of(Resultado resultado)
    {
        return new Respuesta<>(resultado,null);
    }

    public static <T> Respuesta<T> of(T contenido)
    {
        return new Respuesta<>(null, contenido);
    }
}
