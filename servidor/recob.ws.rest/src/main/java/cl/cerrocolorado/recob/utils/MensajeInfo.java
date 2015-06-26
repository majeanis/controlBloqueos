package cl.cerrocolorado.recob.utils;

public class MensajeInfo extends Mensaje
{
    private static final long serialVersionUID = 1L;

    protected MensajeInfo(String codigo, String texto)
    {
        super(codigo, texto, Severidad.INFO);
    }

    protected MensajeInfo(Mensaje another)
    {
        super(another.getCodigo(), another.getTexto(), Severidad.INFO);
    }
}
