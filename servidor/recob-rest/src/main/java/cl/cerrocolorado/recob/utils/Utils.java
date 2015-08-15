package cl.cerrocolorado.recob.utils;

/**
 *
 * @author mauricio.camara
 */
public class Utils
{
    public static <T> T nvl(T object, T valor)
    {
        return (object == null) ? valor: object;
    }
}
