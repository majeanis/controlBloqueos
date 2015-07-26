package cl.cerrocolorado.recob.to;

import java.util.List;

/**
 *
 * @author mauricio.camara
 */
public class LibroBloqueoInfoTO extends LibroBloqueoTO
{
    private List<TagLibroTO> tags;
    private List<EnergiaLibroTO> energias;
    private List<DotacionLibroTO> dotacion;
    private List<RespLibroTO> responsables;

    public List<RespLibroTO> getResponsables()
    {
        return responsables;
    }

    public void setResponsables(List<RespLibroTO> responsables)
    {
        this.responsables = responsables;
    }
    
    public List<DotacionLibroTO> getDotacion()
    {
        return dotacion;
    }

    public void setDotacion(List<DotacionLibroTO> dotacion)
    {
        this.dotacion = dotacion;
    }

    public List<TagLibroTO> getTags()
    {
        return tags;
    }

    public void setTags(List<TagLibroTO> tags)
    {
        this.tags = tags;
    }

    public List<EnergiaLibroTO> getEnergias()
    {
        return energias;
    }

    public void setEnergias(List<EnergiaLibroTO> energias)
    {
        this.energias = energias;
    }

}
