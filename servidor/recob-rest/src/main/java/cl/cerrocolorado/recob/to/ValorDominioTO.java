package cl.cerrocolorado.recob.to;

import cl.cerrocolorado.recob.utils.EntidadTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mauricio.camara
 */
public class ValorDominioTO extends EntidadTO
{
	private static final long serialVersionUID = 1L;

    @JsonIgnore    
    private Integer id;
    private String  codigo;
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

    public String getCodigo()
    {
        return codigo;
    }

    public void setCodigo(String codigo)
    {
        this.codigo = codigo;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
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
        return isIdBlank() && StringUtils.isBlank(codigo);
    }
    
    @Override
    public boolean isIdBlank()
    {
        return id==null || id==0;
    }
}
