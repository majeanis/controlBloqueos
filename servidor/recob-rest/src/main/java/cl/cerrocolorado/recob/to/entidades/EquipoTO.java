package cl.cerrocolorado.recob.to.entidades;

import org.apache.commons.lang3.StringUtils;

import cl.cerrocolorado.recob.utils.EntidadTO;

/**
 *
 * @author mauricio.camara
 */
public class EquipoTO extends EntidadTO
{
	private static final long serialVersionUID = 1L;

    private String codigo;
    private Boolean vigente;
    private UbicacionTO ubicacion;

    public String getCodigo()
    {
        return codigo;
    }

    public void setCodigo(String codigo)
    {
        this.codigo = codigo;
    }

    public Boolean getVigente()
    {
        return vigente;
    }

    public void setVigente(Boolean vigente)
    {
        this.vigente = vigente;
    }

    public UbicacionTO getUbicacion()
    {
        return ubicacion;
    }

    public void setUbicacion(UbicacionTO ubicacion)
    {
        this.ubicacion = ubicacion;
    }

    @Override
    public boolean isKeyBlank()
    {
        return isIdBlank() && (StringUtils.isBlank(codigo) || ubicacion == null || ubicacion.isKeyBlank());
    }    
}
