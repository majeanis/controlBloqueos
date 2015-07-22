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
    private PersonaTO persona;
    private EmpresaTO empresa;
    private Date fechaInicio;
    private Date fechaTermino;
    private UbicacionTO ubicacion;

    public PersonaTO getPersona()
    {
        return persona;
    }

    public void setPersona(PersonaTO persona)
    {
        this.persona = persona;
    }

    public EmpresaTO getEmpresa()
    {
        return empresa;
    }

    public void setEmpresa(EmpresaTO empresa)
    {
        this.empresa = empresa;
    }

    public UbicacionTO getUbicacion()
    {
        return ubicacion;
    }

    public void setUbicacion(UbicacionTO ubicacion)
    {
        this.ubicacion = ubicacion;
    }
    
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
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
        return (id==null || id==0) && (persona == null || persona.isKeyBlank() || fechaInicio == null);
    }
}
