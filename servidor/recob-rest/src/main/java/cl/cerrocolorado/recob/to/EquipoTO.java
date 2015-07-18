/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.cerrocolorado.recob.to;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cl.cerrocolorado.recob.utils.EntidadTO;

/**
 *
 * @author mauricio.camara
 */
public class EquipoTO extends EntidadTO
{
	private static final long serialVersionUID = 1L;

    private Integer id;
    private String codigo;
    private Boolean vigente;
    private UbicacionTO ubicacion;
    private List<TagTO> tags;

    public List<TagTO> getTags()
    {
        return tags;
    }

    public void setTags(List<TagTO> tags)
    {
        this.tags = tags;
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
        return StringUtils.isBlank(codigo) || ubicacion == null || ubicacion.isKeyBlank();
    }    

}
