package cl.cerrocolorado.recob.to;

import cl.cerrocolorado.recob.utils.BaseTO;

/**
 *
 * @author mauricio.camara
 */
public class CandadoTO extends BaseTO
{
	private static final long serialVersionUID = 1L;
	
    private Integer id;
    private String serie;
    private Boolean vigente;
    private ValorDominioTO uso;
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

    public ValorDominioTO getUso()
    {
        return uso;
    }

    public void setUso(ValorDominioTO uso)
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
    
    
}
