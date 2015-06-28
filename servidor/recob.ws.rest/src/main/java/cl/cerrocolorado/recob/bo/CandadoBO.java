package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.to.CandadoTO;
import cl.cerrocolorado.recob.to.PersonaTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.MensajeError;
import cl.cerrocolorado.recob.utils.Resultado;
import java.util.List;

/**
 *
 * @author mauricio.camara
 */
public interface CandadoBO
{
    public Respuesta<CandadoTO> guardar(CandadoTO candado) throws MensajeError;

    public Resultado eliminar(CandadoTO pkCandado) throws MensajeError;

    public CandadoTO get(CandadoTO pkCandado);

    public List<CandadoTO> get(UbicacionTO pkUbicacion);
    
    public List<CandadoTO> get(UbicacionTO pkUbicacion, PersonaTO pkPersona);
}
