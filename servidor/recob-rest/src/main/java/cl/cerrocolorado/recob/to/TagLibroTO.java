package cl.cerrocolorado.recob.to;

import cl.cerrocolorado.recob.utils.EntidadTO;
import java.util.Date;

/**
 *
 * @author mauricio.camara
 */
public class TagLibroTO extends EntidadTO
{
    private Integer id;
    private LibroBloqueoTO libro;
    private TagTO tag;
    private Boolean energiaCero;
    private Date fechaEnergiaCero;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public LibroBloqueoTO getLibro()
    {
        return libro;
    }

    public void setLibro(LibroBloqueoTO libro)
    {
        this.libro = libro;
    }

    public TagTO getTag()
    {
        return tag;
    }

    public void setTag(TagTO tag)
    {
        this.tag = tag;
    }

    public Boolean getEnergiaCero()
    {
        return energiaCero;
    }

    public void setEnergiaCero(Boolean energiaCero)
    {
        this.energiaCero = energiaCero;
    }

    public Date getFechaEnergiaCero()
    {
        return fechaEnergiaCero;
    }

    public void setFechaEnergiaCero(Date fechaEnergiaCero)
    {
        this.fechaEnergiaCero = fechaEnergiaCero;
    }

    
    @Override
    public boolean isKeyBlank()
    {
        return isIdBlank() && (libro==null || libro.isKeyBlank() || tag == null || tag.isKeyBlank());
    }

    @Override
    public boolean isIdBlank()
    {
        return id==null || id==0;
    }
}
