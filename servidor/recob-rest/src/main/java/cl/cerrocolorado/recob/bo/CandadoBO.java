package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.to.entidades.CandadoTO;
import cl.cerrocolorado.recob.to.entidades.PersonaTO;
import cl.cerrocolorado.recob.to.entidades.UbicacionTO;
import cl.cerrocolorado.recob.to.entidades.UsoCandadoTO;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author mauricio.camara
 */
public interface CandadoBO extends BaseBO<CandadoTO>
{
    public Respuesta<CandadoTO> get(UbicacionTO pkUbicacion, String serieCandado);
    
    public Respuesta<List<CandadoTO>> getCandados(UbicacionTO pkUbicacion, 
                                                Optional<Boolean> vigencia);

    public Respuesta<List<CandadoTO>> getCandados(UbicacionTO pkUbicacion, 
                                                Optional<PersonaTO> pkPersona, 
                                                Optional<Boolean> vigencia);
    
    public Respuesta<List<UsoCandadoTO>> getUsosCandado(Optional<Boolean> vigencia);
}
