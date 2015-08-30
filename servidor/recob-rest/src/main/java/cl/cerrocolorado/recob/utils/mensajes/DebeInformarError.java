package cl.cerrocolorado.recob.utils.mensajes;

import cl.cerrocolorado.recob.utils.MensajeError;

/**
 *
 * @author mauricio.camara
 */
public class DebeInformarError extends MensajeError
{
    public DebeInformarError(Class<?> clazz, String nombreParametro)
    {
        super(clazz, "Debe informar %s", nombreParametro );
    }
}
