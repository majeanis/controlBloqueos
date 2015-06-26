package cl.cerrocolorado.recob.utils;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class Rut implements Serializable, Comparable<Rut>
{

    private static final long serialVersionUID = 1L;

    private final Long        numero;
    private final String      digito;

    /**
     * Calcula el Dígito Verificador para un Número de RUT dado
     * 
     * @param numero
     *            Número respecto al cual se debe calcular el dígito
     * @return Dígito verificador calculado
     */
    public static String calcularDigito(Long numero)
    {
        if( numero == null )
            return null;

        int suma = 0;

        for (int serie = 2; numero != 0; numero /= 10)
        {
            suma += (numero % 10) * serie++;
            if( serie > 7 )
                serie = 2;
        }

        int resto = 11 - suma%11;
        switch( resto )
        {
            case 11:
                return "0";
            case 10:
                return "K";
            default:
                return String.valueOf(resto); 
                    
        }
    }

    /**
     * Valida si el Objeto cumple con los valores de un RUT, como 
     * también que el dígito verificador corresponde al número.
     * 
     * @param rut
     *            Objeto Rut que será validado
     * 
     * @return true si el Objeto cumple los valores de RUT
     */
    public static boolean isValid(Rut rut)
    {
        if (rut == null)
            return false;
        
        if( rut.numero == null || rut.digito == null)
            return false;
        
        String digito = calcularDigito(rut.numero);
        
        return digito.equalsIgnoreCase(rut.digito); 
    }
    
    /**
     * Valida si el String cumple con el Formato de un RUT, como también
     * que el dígito verificador corresponde al número del RUT
     * 
     * @param rut
     *            String que será validado
     * 
     * @return true si el String cumple el formato de RUT
     */
    public static boolean isValid(String rut)
    {
        return isValid( Rut.valueOf(rut) );
    }

    /**
     * Convierte un String en formato de RUT en un objeto Rut. Si el String no
     * tiene formato de RUT, entonces el objeto Rut retornado será NULL;
     * 
     * @param s
     *            String que será usado para la conversión
     * @return Objeto Rut
     */
    public static Rut valueOf(String rut)
    {

        if (rut == null)
        {
            return new Rut();
        }

        int guion = rut.indexOf('-');
        Long n = null;
        String d = null;

        try
        {
            if (guion < 0)
            {
                n = Long.parseLong(rut);
            } else
            {
                d = StringUtils.mid(rut, guion + 1, 1);
                n = Long.parseLong(rut.substring(0, guion));
            }
        } catch (NumberFormatException e)
        {
            n = null;
        }

        return new Rut(n,d);
    }

    public Rut()
    {
        this.numero = null;
        this.digito = null;
    }

    public Rut(Long numero, String digito)
    {
        this.numero = numero;
        this.digito = StringUtils.left(digito, 1);
    }
    
    /**
     * Transforma a Texto un objeto RUT aplicando el formato N-D
     * 
     * @param rut
     *            Objeto Rut que será convertido a texto
     * 
     * @return RUT en formato N-D
     */
    public static String toText(Rut rut)
    {
        if( rut == null )
            return "";
        
        String n = (rut.numero == null ? "" : String.valueOf(rut.numero));
        String d = (rut.digito == null ? "" : StringUtils.left(rut.digito, 1));
        String g = (rut.numero == null & rut.digito == null ? "" : "-");

        return n + g + d;
    }

    public Rut(Rut another)
    {
        if (another == null)
        {
            this.numero = null;
            this.digito = null;
        } else
        {
            this.numero = another.numero;
            this.digito = another.digito;
        }
    }

    public Long getNumero()
    {
        return this.numero;
    }

    public String getDigito()
    {
        return this.digito;
    }

    /**
     * Convierte a un String en formato RUT (N-D)
     * 
     * @return Objeto Rut en formato texto (N-D)
     */
    public String toText()
    {
        return toText(this);
    }

    public String toString()
    {
        return "[numero=" + numero + ",digito=" + digito + "]";
    }

    @Override
    public int compareTo(Rut another)
    {
        if (another == null)
            return -1;

        Long n1 = (this.numero == null ? 0 : this.numero);
        Long n2 = (another.numero == null ? 0 : another.numero);
        String d1 = (this.digito == null ? "" : this.digito);
        String d2 = (another.digito == null ? "" : another.digito);

        if (n1 == n2 && d1.equalsIgnoreCase(d2))
            return 0;
        else if (n2 < n1)
            return -1;
        else
            return 1;
    }

    public int compareTo(String another)
    {
        Rut rut = Rut.valueOf(another);
        return compareTo(rut);
    }

    public boolean equals(Object anObject)
    {
        if (this == anObject)
            return true;

        if (anObject instanceof Rut)
        {
            return (this.compareTo((Rut) anObject) == 0);
        }

        return false;
    }

    public boolean isValid()
    {
        return isValid(this);
    }

    /**
     * Determina si el objeto tiene alguna de sus propiedades en NULL (número o dígito)
     * 
     * @return  true si el número o el dígito están en NULL
     */
    public boolean isEmpty()
    {
        return numero == null || digito == null;
    }
}
