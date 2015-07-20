package cl.cerrocolorado.recob.to;

import cl.cerrocolorado.recob.utils.EntidadTO;
import cl.cerrocolorado.recob.utils.Rut;

public class EmpresaTO extends EntidadTO
{
	private static final long serialVersionUID = 1L;
	
    private Integer id;
    private Rut     rut;
    private String  nombre;
    private Boolean vigente;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Rut getRut() {
        return rut;
    }

    public void setRut(Rut rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getVigente() {
        return vigente;
    }

    public void setVigente(Boolean vigente) {
        this.vigente = vigente;
    }
    
    @Override
    public boolean isKeyBlank()
    {
        return (id == null || id == 0) && Rut.isBlank(rut);
    }    
}
