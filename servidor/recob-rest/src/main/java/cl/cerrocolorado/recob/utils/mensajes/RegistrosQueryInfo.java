package cl.cerrocolorado.recob.utils.mensajes;

import cl.cerrocolorado.recob.utils.MensajeInfo;

/**
 *
 * @author mauricio.camara
 */
public class RegistrosQueryInfo extends MensajeInfo
{
    public RegistrosQueryInfo(Class<?> clazz, int registros)
    {
        super(clazz, "Se han encontrado %d registros", registros);
    }
}
