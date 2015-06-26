package cl.cerrocolorado.recob.utils;

import java.time.Instant;

import org.apache.commons.configuration.Configuration;

public class MensajeFactory
{
    private final Configuration mensajes;

    /**
     * La producción de los mensajes depende de la existencia de archivo .properties
     * que contiene los textos de cada mensaje
     * 
     * @param propertiesMensajes
     */
    public MensajeFactory(Configuration propertiesMensajes)
    {
        this.mensajes = propertiesMensajes;
    }

    /**
     * Construye el Mensaje de Error que debe emitirse cuando se captura una Exception
     * 
     * @param exception
     * @return
     */
    public static Mensaje of(Exception exception)
    {
        int id = Instant.now().hashCode();
        return new MensajeError("00001", "Error interno no controlado. Consulte con el administrador [id=" + id + "]", exception);
    }

    /**
     * Crea un Mensaje, cuyo texto necesita de valores para ser completado
     * 
     * @param clazz     Class que será usada para buscar el texto del mensaje en el .properties
     * @param valores   Valores que deben ser aplicados al texto del mensaje
     * 
     * @return          Objeto Mensaje que será construído
     */
    public Mensaje of(Class<? extends Mensaje> clazz, final String... valores)
    {
        /*
         * Si no existe configuración de errores, entonces se emite mensaje de
         * error genérico
         */
        if (mensajes == null)
        {
            return new MensajeError("00002", "No existe configuración de mensajes");
        }

        String key = clazz.getSimpleName();
        String[] textoProperties = mensajes.getStringArray(key);

        if (textoProperties.length < 2)
        {
            return new MensajeError("00003", "Mensaje [" + key + "] no está configurado");
        }

        String codigo = textoProperties[0].trim();
        String texto = textoProperties[1].trim();

        if (codigo.length() == 0 || texto.length() == 0)
        {
            return new MensajeError("00004", "Mensaje [" + key + "] no está configurado");
        }

        /*
         * Si llegamos a este punto, entonces el mensaje está bien configurado,
         * solo queda generar el texto del mensaje
         */
        texto = ToStringUtils.toString(texto, valores);

        if (clazz.isAssignableFrom(MensajeError.class))
        {
            return new MensajeError(codigo, texto);

        } else if (clazz.isAssignableFrom(MensajeInfo.class))
        {
            return new MensajeInfo(codigo, texto);
        }

        return new Mensaje(codigo, texto, Severidad.OK);
    }

    /**
     * Crea un mensaje cuyo texto no necesita valores para ser completado
     * 
     * @param clazz
     * @return
     */
    public Mensaje of(Class<? extends Mensaje> clazz)
    {
        return of(clazz, "");
    }
}
