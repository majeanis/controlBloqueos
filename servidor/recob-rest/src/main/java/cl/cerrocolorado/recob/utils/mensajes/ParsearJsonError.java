package cl.cerrocolorado.recob.utils.mensajes;

import cl.cerrocolorado.recob.utils.MensajeError;

/**
 *
 * @author mauricio.camara
 */
public class ParsearJsonError extends MensajeError
{
    public ParsearJsonError(Class<?> clazz, String entidad)
    {
        super(clazz, "No se pudo parsear JSON [%s]", entidad );
    }
}
