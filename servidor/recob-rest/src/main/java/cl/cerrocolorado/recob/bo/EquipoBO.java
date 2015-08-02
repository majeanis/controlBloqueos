package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.to.EquipoTO;
import cl.cerrocolorado.recob.to.EquipoTagsTO;
import cl.cerrocolorado.recob.to.TagTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.Resultado;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author mauricio.camara
 */
public interface EquipoBO
{
    public Respuesta<EquipoTagsTO> guardar(EquipoTagsTO equipo) throws Exception;

    public Respuesta<EquipoTO> guardar(EquipoTO equipo) throws Exception;

    public Respuesta<TagTO> guardarTag(TagTO tag) throws Exception;

    public Respuesta<EquipoTO> eliminar(EquipoTO pk) throws Exception;

    public Respuesta<TagTO> eliminarTag(TagTO pk) throws Exception;
    
    public Respuesta<EquipoTagsTO> get(EquipoTO pk);

    public Respuesta<TagTO> getTag(TagTO pk);

    public Respuesta<List<EquipoTO>> getVigentes(UbicacionTO pkUbicacion);
    
    public Respuesta<List<EquipoTO>> getTodos(UbicacionTO pkUbicacion, 
                                              Optional<Boolean> vigencia);
    
    public Respuesta<List<TagTO>> getTagsEnergiaCero(EquipoTO pk);
    
    public Respuesta<List<TagTO>> getTagsVigentes(EquipoTO pk);
    
    public Respuesta<List<TagTO>> getTagsTodos(EquipoTO pk, 
                                               Optional<Boolean> vigencia);
}
