package cl.cerrocolorado.recob.utils;

public class Mensaje extends Throwable
{
    private static final long serialVersionUID = 1L;

    private final String      codigo;
    private final String      texto;
    private final Severidad   severidad;
    private final Exception   exception;

    protected Mensaje(Severidad severidad, String codigo, String textoBase, String...valores)
    {
        this.severidad = severidad;
        this.exception = null;
        this.codigo = codigo;
        this.texto = ToStringUtils.toString(textoBase, valores);
    }

    protected Mensaje(Exception exception, String codigo)
    {
        this.severidad = Severidad.ERROR;
        this.exception = exception;
        this.codigo = codigo;
        this.texto = ToStringUtils.toString("Error no controlado. Consulte con el Administrador [#{1}]", String.valueOf(this.hashCode()) );
    }

    protected Mensaje(Mensaje another)
    {
        this.severidad = another.severidad;
        this.exception = another.exception;
        this.codigo = another.codigo;
        this.texto = another.texto;
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
                "[severidad=" + severidad +
                ",codigo=" + codigo + 
                ",texto=" + texto + 
                ",exception=" + exception + "]";
    }
}
