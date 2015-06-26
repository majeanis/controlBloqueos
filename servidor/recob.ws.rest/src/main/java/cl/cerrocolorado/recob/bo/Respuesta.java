package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.ResultadoProceso;
import cl.cerrocolorado.recob.utils.ToStringUtils;
import java.io.Serializable;
import java.util.Optional;

public class Respuesta<T> implements Serializable
{
    private static final long serialVersionUID = 1L;

    private final Resultado   resultado;
    private final Optional<T> contenido;

    public Respuesta(Resultado resultado, T contenido)
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

    public Respuesta(Resultado resultado)
    {
        this(resultado,null);
    }

    public Respuesta(T contenido)
    {
        this(null,contenido);
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
}
