package cl.cerrocolorado.recob.utils;

public class MensajeInfo extends Mensaje
{
    private static final long serialVersionUID = 1L;

    public MensajeInfo(Class<?> clazz, String textoBase, Object...valores)
    {
        super(Severidad.INFO, clazz.getSimpleName(), textoBase, valores );
    }

    public MensajeInfo(Class<?> clazz, Exception exception)
    {
        super(clazz.getSimpleName(), exception);
    }

    public MensajeInfo(Mensaje another)
    {
        super(Severidad.INFO, another.getCodigo(), another.getTexto() );
    }
}
