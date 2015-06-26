package cl.cerrocolorado.recob.utils;

public class MensajeError extends Mensaje
{
    private static final long serialVersionUID = 1L;

    protected MensajeError(String codigo, String texto)
    {
        super(codigo, texto, Severidad.ERROR);
    }

    protected MensajeError(String codigo, String texto, Exception exception)
    {
        super(codigo, texto, exception);
    }

    protected MensajeError(Mensaje another)
    {
        super(another.getCodigo(), another.getTexto(), Severidad.ERROR);
    }
}
