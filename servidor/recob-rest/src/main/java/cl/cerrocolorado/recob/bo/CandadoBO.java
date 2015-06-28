package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.to.CandadoTO;
import cl.cerrocolorado.recob.to.PersonaTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.Resultado;
import java.util.List;

/**
 *
 * @author mauricio.camara
 */
public interface CandadoBO
{
    public Respuesta<CandadoTO> guardar(CandadoTO candado);

    public Resultado eliminar(CandadoTO pkCandado);

    public CandadoTO get(CandadoTO pkCandado);

    public List<CandadoTO> getVigentes(UbicacionTO pkUbicacion);
    
    public List<CandadoTO> getVigentes(UbicacionTO pkUbicacion, PersonaTO pkPersona);
    
    public List<CandadoTO> getTodos(UbicacionTO pkUbicacion);
}
