package cl.cerrocolorado.recob.utils;

public enum Severidad
{
    OK(1, "O"), 
    INFO(2, "I"), 
    ERROR(4, "E");

    private final int    id;
    private final String codigo;

    Severidad(int id, String codigo)
    {
        this.id = id;
        this.codigo = codigo;
    }

    public int getId()
    {
        return id;
    }

    public String getCodigo()
    {
        return codigo;
    }

    public static Severidad fromId(int id)
    {
        switch (id)
        {
            case 1:
                return Severidad.OK;
            case 2:
                return Severidad.INFO;
            case 4:
                return Severidad.ERROR;
        }

        return null;
    }

    public static Severidad fromCodigo(String codigo)
    {
        if (codigo.equalsIgnoreCase(Severidad.OK.getCodigo()))
            return Severidad.OK;

        if (codigo.equalsIgnoreCase(Severidad.INFO.getCodigo()))
            return Severidad.INFO;

        if (codigo.equalsIgnoreCase(Severidad.ERROR.getCodigo()))
            return Severidad.ERROR;

        return null;
    }
}
