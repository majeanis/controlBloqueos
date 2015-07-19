package cl.cerrocolorado.recob.to;

import cl.cerrocolorado.recob.utils.EntidadTO;
import java.util.Date;

/**
 *
 * @author mauricio.camara
 */
public class ResponsableTO extends EntidadTO
{
    private Integer id;
    private TrabajadorTO trabajador;
    private Date fechaInicio;
    private Date fechaTermino;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public TrabajadorTO getTrabajador()
    {
        return trabajador;
    }

    public void setTrabajador(TrabajadorTO trabajador)
    {
        this.trabajador = trabajador;
    }

    public Date getFechaInicio()
    {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio)
    {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaTermino()
    {
        return fechaTermino;
    }

    public void setFechaTermino(Date fechaTermino)
    {
        this.fechaTermino = fechaTermino;
    }
    
    @Override
    public boolean isKeyBlank()
    {
        return trabajador == null || trabajador.isKeyBlank() || fechaInicio == null;
    }
}
