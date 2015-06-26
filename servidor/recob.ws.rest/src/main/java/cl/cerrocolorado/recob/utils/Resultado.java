package cl.cerrocolorado.recob.utils;

import java.io.Serializable;
import java.util.List;

public interface Resultado extends Serializable
{
    default boolean esExitoso()
    {
        Severidad severidad = getSeveridad();
        return (severidad == Severidad.OK || severidad == Severidad.INFO);
    }

    public boolean hayExceptions();

    public Severidad getSeveridad();

    public List<Mensaje> getMensajes();

    default Mensaje addMensaje(Exception exception)
    {
        return addMensaje(MensajeFactory.of(exception));
    }

    public Mensaje addMensaje(Mensaje mensaje);

    default void append(Resultado another)
    {
        if (another == null)
            return;

        List<Mensaje> newMensajes = another.getMensajes();
        for (Mensaje mensaje : newMensajes)
        {
            addMensaje(mensaje);
        }
    }
}
