package cl.cerrocolorado.recob.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ToStringUtils
{
    public static String toString(Object objeto)
    {
        return ToStringBuilder.reflectionToString(objeto, ToStringStyle.SHORT_PREFIX_STYLE, true);
    }

    public static String toString(Exception exception)
    {
        if (exception == null)
            return "";

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);

        exception.printStackTrace(printWriter);
        return writer.toString();
    }

    public static String toString(Integer value)
    {
        if(value == null)
            return "";
        
        return String.valueOf(value);
    }
    
    public static String toString(Long value)
    {
        if (value == null)
            return "";

        return String.valueOf(value);
    }

    public static String toString(String cadena)
    {
        if (cadena == null)
            return "";
        return cadena;
    }

    /**
     * Permite generar un String en base a todas las entradas contenidas en un
     * Map, las cuales a su vez también son convertidas a String.
     * 
     * @param map
     *            Map que será convertido a String
     * @return Cadena con la representación del Map
     */
    public static String toString(Map<String, Object> map)
    {
        if (map == null)
            return "";

        StringBuilder out = new StringBuilder(map.getClass().getSimpleName() + "[");

        out.append("size=" + map.size());
        for (Entry<String, Object> entry : map.entrySet())
        {
            if (entry.getValue() != map)
            {
                out.append("," + entry.getKey() + "=" + entry.getValue());
            }
        }

        return out.toString() + "]";
    }

    /**
     * Toma un Texto Base y aplica los valores dados como argumentos, los cuales
     * se posicionan en los lugares indicados en el texto base, lo cual se hace
     * incluyendo la expresión "#{n}" dentro del texto base.
     * 
     * @param textoBase
     *            Texto que contiene la cadena donde se deben aplicar los
     *            valores
     * @param valores
     *            Valores que se debe aplicar en el Texto Base
     * @return Cadena que resulta de la combinación del textBase y valores
     */
    public static String toString(String textoBase, final Object... valores)
    {
        if (textoBase == null)
            return "";

        String textoFormateado = textoBase.trim();

        if (valores == null)
            return textoFormateado;

        for (int i = 0; i < valores.length; i++)
        {
            String values = toString(valores[i]);
            textoFormateado = textoFormateado.replace("#{" + (i + 1) + "}", values);
        }

        /*
         * Una vez puestos los valores en el texto base, se procede a eliminar
         * todas las posibles marcas que no fueron reemplazadas, producto que no
         * se dieron todos los valores necesarios, de tal manera que en el
         * String resultante no queden cadenas del tipo "#{n}".
         */
        textoFormateado = textoFormateado.replaceAll("\\#\\{[1-9]+\\}", "");
        textoFormateado = textoFormateado.replace("  ", " ");

        return textoFormateado;
    }

    /**
     * Permite generar un String en base a la concatenación de distintos valores
     * dados, usando un caracter también dado como parámetro.
     * 
     * @param valores
     *            Cadenas que deben ser unidas
     * @param separador
     *            Caracter que se usará como separador de las cadenas
     * 
     * @return String resultante de la concatenación
     */
    public static String toString(String[] valores, String separador)
    {
        if (valores == null)
            return "";

        StringBuilder sb = new StringBuilder();
        String sep = toString(separador);

        for (String st : valores)
        {
            sb.append(st + sep);
        }

        if (valores.length != 0)
            sb.deleteCharAt(sb.length() - sep.length());

        return sb.toString();
    }
}
