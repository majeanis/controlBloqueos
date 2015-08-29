package cl.cerrocolorado.recob.utils;

public enum Severidad
{
    OK, 
    ERROR;

    public static Severidad of(int id)
    {
        switch (id)
        {
            case 0:
                return Severidad.OK;
            case 1:
                return Severidad.ERROR;
        }

        return null;
    }

    public static Severidad of(String codigo)
    {
        if (codigo.equalsIgnoreCase("OK"))
            return Severidad.OK;

        if (codigo.equalsIgnoreCase("ERROR"))
            return Severidad.ERROR;

        return null;
    }
}
