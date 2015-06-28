package cl.cerrocolorado.recob.utils;

public class MensajeError extends Mensaje
{
    private static final long serialVersionUID = 1L;

    public MensajeError(Class<?> clazz, String textoBase, String...valores)
    {
        super(Severidad.ERROR, clazz.getSimpleName(), textoBase, valores );
    }

    public MensajeError(Exception exception, Class<?> clazz)
    {
        super(exception, clazz.getSimpleName());
    }

    public MensajeError(Mensaje another)
    {
        super(Severidad.ERROR, another.getCodigo(), another.getTexto() );
    }
}
