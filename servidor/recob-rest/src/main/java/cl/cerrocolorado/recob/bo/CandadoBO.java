package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.to.CandadoTO;
import cl.cerrocolorado.recob.to.PersonaTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.to.UsoCandadoTO;
import cl.cerrocolorado.recob.utils.Transaccional;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author mauricio.camara
 */
public interface CandadoBO extends BaseBO<CandadoTO>
{
    public Respuesta<CandadoTO> get(UbicacionTO pkUbicacion, String serieCandado);
    
    public Respuesta<List<CandadoTO>> getTodos(UbicacionTO pkUbicacion, 
                                               Optional<Boolean> vigencia);

    public Respuesta<List<CandadoTO>> getTodos(UbicacionTO pkUbicacion, 
                                               Optional<PersonaTO> pkPersona, 
                                               Optional<Boolean> vigencia);

    public Respuesta<List<CandadoTO>> getVigentes(UbicacionTO pkUbicacion);
    
    public Respuesta<List<CandadoTO>> getVigentes(UbicacionTO pkUbicacion,
                                                  Optional<PersonaTO> pkPersona);
    
    public Respuesta<List<UsoCandadoTO>> getUsosCandado(Optional<Boolean> vigencia);
}
