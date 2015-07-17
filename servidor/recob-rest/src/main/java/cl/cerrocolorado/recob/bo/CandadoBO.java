package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.to.CandadoTO;
import cl.cerrocolorado.recob.to.PersonaTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.Transaccional;

import java.util.List;

/**
 *
 * @author mauricio.camara
 */
public interface CandadoBO
{
	@Transaccional
    public Respuesta<CandadoTO> guardar(CandadoTO candado) throws Exception; 

	@Transaccional
    public Resultado eliminar(CandadoTO pkCandado) throws Exception;

    public Respuesta<CandadoTO> get(CandadoTO pkCandado);
    
    public Respuesta<CandadoTO> get(UbicacionTO pkUbicacion, String serieCandado);
    
    public List<CandadoTO> getVigentes(UbicacionTO pkUbicacion);
    
    public List<CandadoTO> getVigentes(UbicacionTO pkUbicacion, PersonaTO pkPersona);
    
    public List<CandadoTO> getTodos(UbicacionTO pkUbicacion);

    public List<CandadoTO> getTodos(UbicacionTO pkUbicacion, PersonaTO pkPersona);
}
