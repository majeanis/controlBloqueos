package cl.cerrocolorado.recob.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.DirectFieldAccessor;

/**
 *
 * @author mauricio.camara
 */
public class ValidUtils
{
    public static boolean isPropertyNull(Object objeto, String nombrePropiedad)
    {
        if(objeto==null)
        {
            return true;
        }
        
        DirectFieldAccessor dfa = new DirectFieldAccessor(objeto);
        Object value = dfa.getPropertyValue(nombrePropiedad);
        
        return value == null;
    }
    
    public static boolean isPropertyBlank(Object objeto, String nombrePropiedad)
    {
        if( isPropertyNull( objeto, nombrePropiedad) )
        {
            return true;
        }
        
        DirectFieldAccessor dfa = new DirectFieldAccessor(objeto);
        Object value = dfa.getPropertyValue(nombrePropiedad);

        if( String.class.isAssignableFrom(value.getClass()) )
        {
            String valor = (String) value;
            return StringUtils.isBlank(valor);
        } else if( Rut.class.isAssignableFrom(value.getClass()) )
        {
            return Rut.isBlank((Rut) value);
        }
        
        return false;
    }
    
    public static boolean hayNulls(Object...objetos)
    {
        for (Object objeto : objetos)
        {
            if (objeto == null)
            {
                return true;
            }
        }
        
        return false;
    }
}
