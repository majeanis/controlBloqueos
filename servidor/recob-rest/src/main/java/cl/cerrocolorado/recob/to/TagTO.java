package cl.cerrocolorado.recob.to;

import cl.cerrocolorado.recob.utils.EntidadTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author mauricio.camara
 */
public class TagTO extends EntidadTO
{
	private static final long serialVersionUID = 1L;

	private Integer id;
    private Integer numero;
    private String nombre;
    private String descripcion;
    private Boolean energiaCero;
    private Boolean vigente;
    private EquipoTO equipo;

    @JsonIgnore
    public EquipoTO getEquipo()
    {
        return equipo;
    }

    public void setEquipo(EquipoTO equipo)
    {
        this.equipo = equipo;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getNumero()
    {
        return numero;
    }

    public void setNumero(Integer numero)
    {
        this.numero = numero;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public Boolean getEnergiaCero()
    {
        return energiaCero;
    }

    public void setEnergiaCero(Boolean energiaCero)
    {
        this.energiaCero = energiaCero;
    }

    public Boolean getVigente()
    {
        return vigente;
    }

    public void setVigente(Boolean vigente)
    {
        this.vigente = vigente;
    }

    @Override
    public boolean isKeyBlank()
    {
        return (id==null || id==0) && (numero==null || numero==0 || equipo.isKeyBlank());
    }
}
