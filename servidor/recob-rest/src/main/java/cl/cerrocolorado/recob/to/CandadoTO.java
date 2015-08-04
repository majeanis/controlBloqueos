package cl.cerrocolorado.recob.to;

import cl.cerrocolorado.recob.utils.EntidadTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author mauricio.camara
 */
public class CandadoTO extends EntidadTO
{
	private static final long serialVersionUID = 1L;
	
    @JsonIgnore
    private Integer id;
    private Integer numero;
    private String serie;
    private Boolean vigente;
    private UsoCandadoTO uso;
    private PersonaTO persona;
    private UbicacionTO ubicacion;

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

    public String getSerie()
    {
        return serie;
    }

    public void setSerie(String serie)
    {
        this.serie = serie;
    }

    public Boolean getVigente()
    {
        return vigente;
    }

    public void setVigente(Boolean vigente)
    {
        this.vigente = vigente;
    }

    public UsoCandadoTO getUso()
    {
        return uso;
    }

    public void setUso(UsoCandadoTO uso)
    {
        this.uso = uso;
    }

    public PersonaTO getPersona()
    {
        return persona;
    }

    public void setPersona(PersonaTO persona)
    {
        this.persona = persona;
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
        return isIdBlank() && (numero==null || numero==0 || ubicacion == null || ubicacion.isKeyBlank());
    }

    @Override
    public boolean isIdBlank()
    {
        return id==null || id==0;
    }
}
