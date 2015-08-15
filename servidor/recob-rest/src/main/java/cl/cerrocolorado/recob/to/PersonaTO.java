package cl.cerrocolorado.recob.to;

import cl.cerrocolorado.recob.utils.EntidadTO;
import cl.cerrocolorado.recob.utils.Rut;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.StringUtils;

public class PersonaTO extends EntidadTO
{
	private static final long serialVersionUID = 1L;

    @JsonIgnore	
    private Integer id;
    private Rut rut;
    private String nombre;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Rut getRut()
    {
        return rut;
    }

    public void setRut(Rut rut)
    {
        this.rut = rut;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    @Override
    public boolean isKeyBlank()
    {
        return isIdBlank() && (rut==null || rut.isBlank());
    }

    @Override
    public boolean isIdBlank()
    {
        return id==null || id==0;
    }
}
