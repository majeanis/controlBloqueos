package cl.cerrocolorado.recob.utils;

public class Mensaje extends Throwable
{
    private static final long serialVersionUID = 1L;

    private final String      codigo;
    private final String      texto;
    private final Severidad   severidad;
    private final Exception   exception;

    protected Mensaje(Severidad severidad, String codigo, String textoBase, Object...valores)
    {
        this.severidad = severidad;
        this.exception = null;
        this.codigo = codigo;
        this.texto = ToStringUtils.format(textoBase, valores);
    }

    protected Mensaje(String codigo, Exception exception)
    {
        this.severidad = Severidad.ERROR;
        this.exception = exception;
        this.codigo = codigo;
        this.texto = ToStringUtils.format("Error no esperado. Consulte con el Administrador [%d]", this.hashCode() );
    }

    protected Mensaje(String codigo, String textoBase, Object...valores)
    {
        this.severidad = Severidad.OK;
        this.exception = null;
        this.codigo = codigo;
        this.texto = ToStringUtils.format(textoBase, valores);
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
