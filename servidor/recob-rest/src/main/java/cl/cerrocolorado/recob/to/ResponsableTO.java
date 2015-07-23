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
    private Date fechaIngreso;
    private Date fechaSalida;
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

    public Date getFechaIngreso()
    {
        return fechaIngreso;
    }

    public void setFechaIngeso(Date fechaIngreso)
    {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaSalida()
    {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida)
    {
        this.fechaSalida = fechaSalida;
    }
    
    @Override
    public boolean isKeyBlank()
    {
        return (id==null || id==0) && (persona == null || persona.isKeyBlank() || fechaIngreso == null);
    }
}
