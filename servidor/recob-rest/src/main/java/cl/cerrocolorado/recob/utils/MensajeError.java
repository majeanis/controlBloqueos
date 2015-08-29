package cl.cerrocolorado.recob.utils;

public class MensajeError extends Mensaje
{
    private static final long serialVersionUID = 1L;

    public MensajeError(Class<?> clazz, String textoBase, Object...valores)
    {
        super(Severidad.ERROR, clazz.getSimpleName(), textoBase, valores );
    }

    public MensajeError(Class<?> clazz, Exception exception)
    {
        super(clazz.getSimpleName(), exception);
    }

    public MensajeError(Mensaje another)
    {
        super(Severidad.ERROR, another.getCodigo(), another.getTexto() );
    }
}
