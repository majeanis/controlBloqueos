package cl.cerrocolorado.recob.utils;

import java.util.HashMap;
import java.util.List;

public class ResultadoMap extends HashMap<String, Object> implements Resultado
{
    private static final long      serialVersionUID = 1L;

    private final ResultadoProceso resultado;

    public ResultadoMap()
    {
        super();
        resultado = new ResultadoProceso();
    }

    public ResultadoMap(Resultado another)
    {
        super();
        this.resultado = new ResultadoProceso(another);
    }

    public ResultadoMap(ResultadoMap another)
    {
        super();
        this.resultado = new ResultadoProceso(another);
        this.putAll(another);
    }

    @Override
    public boolean esExitoso()
    {
        return resultado.esExitoso();
    }

    @Override
    public void append(Resultado another)
    {
        resultado.append(another);
    }

    public void append(ResultadoMap another)
    {
        resultado.append(another);
        this.putAll(another);
    }

    @Override
    public Severidad getSeveridad()
    {
        return resultado.getSeveridad();
    }

    @Override
    public List<Mensaje> getMensajes()
    {
        return resultado.getMensajes();
    }

    @Override
    public Mensaje addMensaje(Mensaje mensaje)
    {
        return resultado.addMensaje(mensaje);
    }

    @Override
    public boolean hayExceptions()
    {
        return resultado.hayExceptions();
    }

    @Override
    public String toString()
    {
        return ToStringUtils.toString(this);
    }

    @Override
    public Mensaje addException(Exception exception, Class<?> clazz)
    {
        return resultado.addException(exception, clazz);
    }

    @Override
    public Mensaje addError(Class<?> clazz, String textoBase, String... valores)
    {
        return resultado.addError(clazz, textoBase, valores);
    }

    @Override
    public Mensaje addMensaje(Class<?> clazz, String textoBase, String... valores)
    {
        return resultado.addMensaje(clazz, textoBase, valores);
    }
}
