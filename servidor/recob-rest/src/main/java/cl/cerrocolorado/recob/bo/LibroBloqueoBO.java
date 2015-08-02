package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.DotacionLibroTO;
import cl.cerrocolorado.recob.to.EnergiaLibroTO;
import cl.cerrocolorado.recob.to.LibroBloqueoInfoTO;
import cl.cerrocolorado.recob.to.LibroBloqueoTO;
import cl.cerrocolorado.recob.to.RespLibroTO;
import cl.cerrocolorado.recob.to.TagLibroTO;
import java.util.Date;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author mauricio.camara
 */
public interface LibroBloqueoBO
{
    public Respuesta<LibroBloqueoTO> guardar(LibroBloqueoTO libro) throws Exception;
    public Respuesta<LibroBloqueoTO> get(LibroBloqueoTO pk);
    public Respuesta<LibroBloqueoInfoTO> guardarLibro(LibroBloqueoInfoTO libro) throws Exception;;
    public Respuesta<LibroBloqueoInfoTO> getLibro(LibroBloqueoTO pk);
    public Respuesta<List<LibroBloqueoTO>> getVigentes(CajaBloqueoTO pk, Optional<Date> fechaLibro);
    public Respuesta<List<LibroBloqueoTO>> getCerrados(CajaBloqueoTO pk, Optional<Date> fechaCierre);

    public Respuesta<TagLibroTO> getTag(TagLibroTO pk);
    public Respuesta<TagLibroTO> guardarTag(TagLibroTO tag) throws Exception;
    public Respuesta<List<TagLibroTO>> getTags(LibroBloqueoTO pk, Optional<Boolean> energiaCero);
    public Respuesta<List<TagLibroTO>> guardarTags(List<TagLibroTO> tags) throws Exception;
    public Respuesta<TagLibroTO> eliminarTag(TagLibroTO pk) throws Exception;
    
    public Respuesta<EnergiaLibroTO> guardarEnergia(LibroBloqueoTO pk);
    public Respuesta<EnergiaLibroTO> getEnergia(EnergiaLibroTO pk);
    public Respuesta<List<EnergiaLibroTO>> getEnergias(LibroBloqueoTO pk);
    public Respuesta<List<EnergiaLibroTO>> guardarEnergias(List<EnergiaLibroTO> energias) throws Exception;;
    public Respuesta<EnergiaLibroTO> eliminarEnergia(EnergiaLibroTO pk) throws Exception;;
    
    public Respuesta<List<DotacionLibroTO>> getDotaciones(LibroBloqueoTO pk);
    public Respuesta<DotacionLibroTO> guardarDotacion(DotacionLibroTO dotacion) throws Exception;;
    public Respuesta<DotacionLibroTO> eliminarDotacion(DotacionLibroTO pk);
    
    public Respuesta<List<RespLibroTO>> getResponsables(LibroBloqueoTO pk);
    public Respuesta<RespLibroTO> asignarResponsable(RespLibroTO responsable) throws Exception;;
}
