package cl.cerrocolorado.recob.utils.mensajes;

import cl.cerrocolorado.recob.utils.MensajeInfo;

/**
 *
 * @author mauricio.camara
 */
public class ParsearJsonError extends MensajeInfo
{
    public ParsearJsonError(Class<?> clazz, String entidad)
    {
        super(clazz, "No se pudo parsear JSON [#{1}]", entidad );
    }
}
