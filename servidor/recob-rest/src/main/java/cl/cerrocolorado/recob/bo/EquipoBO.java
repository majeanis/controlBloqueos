package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.to.EquipoTO;
import cl.cerrocolorado.recob.to.TagTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.Transaccional;

import java.util.List;

/**
 *
 * @author mauricio.camara
 */
public interface EquipoBO
{
	@Transaccional
    public Respuesta<EquipoTO> guardar(EquipoTO equipo) throws Exception;

	@Transaccional
    public Resultado eliminar(EquipoTO pk) throws Exception;

    public Respuesta<EquipoTO> get(EquipoTO pk);

    public List<EquipoTO> getVigentes(UbicacionTO pkUbicacion);
    
    public List<EquipoTO> getTodos(UbicacionTO pkUbicacion);
    
    public List<TagTO> getTagsEnergiaCero(EquipoTO pk);
    
    public List<TagTO> getTagsVigentes(EquipoTO pk);
}
