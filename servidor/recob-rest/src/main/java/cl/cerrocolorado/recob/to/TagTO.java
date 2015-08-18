package cl.cerrocolorado.recob.to;

import cl.cerrocolorado.recob.utils.EntidadTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author mauricio.camara
 */
public class TagTO extends EntidadTO
{
	private static final long serialVersionUID = 1L;

    @JsonIgnore    
	private Integer id;

    private String codigo;
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

    public String getCodigo()
    {
        return codigo;
    }

    public void setCodigo(String codigo)
    {
        this.codigo = codigo;
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
        return isIdBlank() && (StringUtils.isBlank(codigo) || equipo.isKeyBlank());
    }

    @Override
    public boolean isIdBlank()
    {
        return id==null || id==0;
    }
}
