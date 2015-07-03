package cl.cerrocolorado.recob.rest.utils;

import cl.cerrocolorado.recob.utils.ToStringUtils;

public class RespMensaje
{
    private String codigo;
    private String testo;
    private String severidad;

    public String getCodigo()
    {
        return codigo;
    }
    public void setCodigo(String codigo)
    {
        this.codigo = codigo;
    }
    public String getTexto()
    {
        return testo;
    }
    public void setTexto(String texto)
    {
        this.testo = texto;
    }
    public String getSeveridad()
    {
        return severidad;
    }
    public void setSeveridad(String severidad)
    {
        this.severidad = severidad;
    }
    public String toString()
    {
        return ToStringUtils.toString(this);
    }
}
