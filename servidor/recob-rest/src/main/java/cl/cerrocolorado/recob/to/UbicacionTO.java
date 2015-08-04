/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.cerrocolorado.recob.to;

import cl.cerrocolorado.recob.utils.EntidadTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author mauricio.camara
 */
public class UbicacionTO extends EntidadTO
{
	private static final long serialVersionUID = 1L;

    @JsonIgnore    
    private Integer id;
    private String nombre;
    private String descripcion;
    private Boolean vigente;
    private EstacionTO estacion;

    @JsonIgnore
    private String token;
        
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

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public EstacionTO getEstacion()
    {
        return estacion;
    }

    public void setEstacion(EstacionTO estacion)
    {
        this.estacion = estacion;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
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
