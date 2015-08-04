package cl.cerrocolorado.recob.to;

import cl.cerrocolorado.recob.utils.EntidadTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author mauricio.camara
 */
public class CajaBloqueoTO extends EntidadTO
{
	private static final long serialVersionUID = 1L;

    @JsonIgnore
	private Integer id;
    private Integer numero;
    private String nombre;
    private Boolean vigente;
    private UbicacionTO ubicacion;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
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

    public Integer getNumero()
    {
        return numero;
    }

    public void setNumero(Integer numero)
    {
        this.numero = numero;
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
        return isIdBlank() && (numero == null || numero == 0 || ubicacion == null || ubicacion.isKeyBlank());
    }

    @Override
    public boolean isIdBlank()
    {
        return id==null || id==0;
    }
}
