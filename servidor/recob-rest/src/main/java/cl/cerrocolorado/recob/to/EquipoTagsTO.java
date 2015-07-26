package cl.cerrocolorado.recob.to;

import java.util.List;

/**
 *
 * @author mauricio.camara
 */
public class EquipoTagsTO extends EquipoTO
{
    private static final long serialVersionUID = 1L;

    private List<TagTO> tags;

    public List<TagTO> getTags()
    {
        return tags;
    }

    public void setTags(List<TagTO> tags)
    {
        this.tags = tags;
    }
}
