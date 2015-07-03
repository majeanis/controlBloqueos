package cl.cerrocolorado.recob.rest.utils;

import java.util.Date;

import cl.cerrocolorado.recob.utils.ToStringUtils;

public class RespHead
{
    private Date fecha;
    private String codigo;
    private String severidad;

    public Date getFecha()
    {
        return fecha;
    }
    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }
    public String getCodigo()
    {
        return codigo;
    }
    public void setCodigo(String codigo)
    {
        this.codigo = codigo;
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
