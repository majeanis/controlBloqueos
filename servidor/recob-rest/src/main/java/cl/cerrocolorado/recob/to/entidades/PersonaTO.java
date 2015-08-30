package cl.cerrocolorado.recob.to.entidades;

import cl.cerrocolorado.recob.utils.EntidadTO;
import cl.cerrocolorado.recob.utils.Rut;

public class PersonaTO extends EntidadTO
{
	private static final long serialVersionUID = 1L;

    private Rut rut;
    private String nombre;

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
}
