package cl.cerrocolorado.recob.utils;

public class Mensaje extends Throwable
{
    private static final long serialVersionUID = 1L;

    private final String      codigo;
    private final String      texto;
    private final Severidad   severidad;
    private final Exception   exception;

    protected Mensaje(String codigo, String texto, Severidad severidad)
    {
        this.codigo = codigo;
        this.texto = texto;
        this.severidad = severidad;
        this.exception = null;
    }

    protected Mensaje(String codigo, String texto, Exception exception)
    {
        this.codigo = codigo;
        this.texto = texto;
        this.severidad = Severidad.ERROR;
        this.exception = exception;
    }

    protected Mensaje(Mensaje another)
    {
        this.codigo = another.codigo;
        this.texto = another.texto;
        this.severidad = another.severidad;
        this.exception = another.exception;
    }

    public String getCodigo()
    {
        return codigo;
    }

    public String getTexto()
    {
        return texto;
    }

    public Exception getException()
    {
        return exception;
    }

    public Severidad getSeveridad()
    {
        return severidad;
    }

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + 
                "[codigo=" + codigo + 
                ",texto=" + texto + 
                ",severidad=" + severidad + 
                ",exception=" + exception + "]";
    }
}
