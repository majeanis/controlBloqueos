package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.to.EquipoTO;
import cl.cerrocolorado.recob.to.EquipoTagsTO;
import cl.cerrocolorado.recob.to.TagTO;
import cl.cerrocolorado.recob.to.UbicacionTO;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author mauricio.camara
 */
public interface EquipoBO extends BaseBO<EquipoTO>
{
    public Respuesta<List<EquipoTO>> getList(UbicacionTO pkUbicacion, Optional<Boolean> vigencia);
    public Respuesta<EquipoTagsTO> getEquipo(EquipoTO pk);
    
    public Respuesta<TagTO> crearTag(TagTO tag) throws Exception;
    public Respuesta<TagTO> modificarTag(TagTO tag) throws Exception;
    public Respuesta<TagTO> eliminarTag(TagTO pk) throws Exception;
    public Respuesta<TagTO> getTag(TagTO pk);
    
    public Respuesta<List<TagTO>> getTags(EquipoTO pk, Optional<Boolean> vigencia);
    public Respuesta<List<TagTO>> getTagsEnergiaCero(EquipoTO pk);
}
