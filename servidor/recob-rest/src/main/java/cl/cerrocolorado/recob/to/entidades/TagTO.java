package cl.cerrocolorado.recob.to.entidades;

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
}
