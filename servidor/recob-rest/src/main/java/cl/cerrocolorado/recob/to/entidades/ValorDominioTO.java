package cl.cerrocolorado.recob.to.entidades;

import cl.cerrocolorado.recob.utils.EntidadTO;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mauricio.camara
 */
public class ValorDominioTO extends EntidadTO
{
	private static final long serialVersionUID = 1L;

    private String  codigo;
    private String  nombre;
    private Boolean vigente;

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
}
