package cl.cerrocolorado.recob.to;

import cl.cerrocolorado.recob.utils.EntidadTO;

/**
 *
 * @author mauricio.camara
 */
public class FuncionBloqueoTO extends EntidadTO
{
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String nombre;
    private Integer maximo;
    private Boolean vigente;

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

    public Integer getMaximo()
    {
        return maximo;
    }

    public void setMaximo(Integer Maximo)
    {
        this.maximo = Maximo;
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
        return isIdBlank();
    }
    
    @Override
    public boolean isIdBlank()
    {
        return id==null || id==0;
    }
}
