package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.DotacionLibroTO;
import cl.cerrocolorado.recob.to.EnergiaLibroTO;
import cl.cerrocolorado.recob.to.LibroBloqueoInfoTO;
import cl.cerrocolorado.recob.to.LibroBloqueoTO;
import cl.cerrocolorado.recob.to.RespLibroTO;
import cl.cerrocolorado.recob.to.TagLibroTO;
import cl.cerrocolorado.recob.utils.Resultado;
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

    public Respuesta<LibroBloqueoInfoTO> getLibro(LibroBloqueoTO pk);
    
    public Respuesta<List<LibroBloqueoTO>> getVigentes(CajaBloqueoTO pk,
                                                       Optional<Date> fechaLibro);

    public Respuesta<List<LibroBloqueoTO>> getCerrados(CajaBloqueoTO pk,
                                                       Optional<Date> fechaCierre);
    
    public Respuesta<List<TagLibroTO>> getTags(LibroBloqueoTO pk,
                                               Optional<Boolean> energiaCero);
    
    public Respuesta<TagLibroTO> agregarTag(TagLibroTO tag);

    public Resultado eliminarTag(TagLibroTO pk);
    
    public Respuesta<List<EnergiaLibroTO>> getEnergias(LibroBloqueoTO pk);
    
    public Respuesta<EnergiaLibroTO> agregarEnergia(EnergiaLibroTO energia);
    
    public Resultado eliminarEnergia(EnergiaLibroTO pk);

    public Respuesta<List<DotacionLibroTO>> getDotaciones(LibroBloqueoTO pk);

    public Respuesta<DotacionLibroTO> guardarDotacion(DotacionLibroTO dotacion);
    
    public Resultado eliminarDotacion(DotacionLibroTO pk);
    
    public Respuesta<List<RespLibroTO>> getResponsables(LibroBloqueoTO pk);
    
    public Respuesta<RespLibroTO> asignarResponsable(RespLibroTO responsable);
}
