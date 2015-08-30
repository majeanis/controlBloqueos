package cl.cerrocolorado.recob.to.entidades;

import cl.cerrocolorado.recob.utils.EntidadTO;
import java.util.Date;

/**
 *
 * @author mauricio.camara
 */
public class ResponsableTO extends EntidadTO
{
    private static final long serialVersionUID = 1L;

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
        return isIdBlank() && (persona == null || persona.isKeyBlank() || fechaIngreso == null);
    }
}
