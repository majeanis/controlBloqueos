package cl.cerrocolorado.recob.utils.mensajes;

import cl.cerrocolorado.recob.utils.MensajeInfo;

/**
 *
 * @author mauricio.camara
 */
public class DebeInformarError extends MensajeInfo
{
    public DebeInformarError(Class<?> clazz, String nombreParametro)
    {
        super(clazz, "Debe informar %s", nombreParametro );
    }
}
