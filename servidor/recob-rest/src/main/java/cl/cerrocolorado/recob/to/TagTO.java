/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.cerrocolorado.recob.to;

/**
 *
 * @author mauricio.camara
 */
public class TagTO
{
    private Integer id;
    private Integer numero;
    private String nombre;
    private String descripcion;
    private Boolean energiaCero;
    private Boolean vigente;
    private Integer idEquipo;

    public Integer getIdEquipo()
    {
        return idEquipo;
    }

    public void setIdEquipo(Integer idEquipo)
    {
        this.idEquipo = idEquipo;
    }
    
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

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
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
}
