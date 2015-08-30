package cl.cerrocolorado.recob.to.entidades;

import cl.cerrocolorado.recob.utils.EntidadTO;
import java.util.Date;

/**
 *
 * @author mauricio.camara
 */
public class TagLibroTO extends EntidadTO
{
    private LibroBloqueoTO libro;
    private TagTO tag;
    private Boolean energiaCero;
    private Date fechaEnergiaCero;

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
}
