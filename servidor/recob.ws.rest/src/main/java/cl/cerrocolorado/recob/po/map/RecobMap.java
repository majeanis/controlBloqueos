package cl.cerrocolorado.recob.po.map;

import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.CandadoTO;
import cl.cerrocolorado.recob.to.PersonaTO;
import cl.cerrocolorado.recob.to.TrabajadorTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mauricio.camara
 */
public interface RecobMap
{
    /*
     * Manipulación de Ubicaciones
     */
    public UbicacionTO selectUbicacion(UbicacionTO ubicacion);

    /*
     * Manipulación de Cajas de Bloqueo
     */ 
    public void insertCajaBloqueo(CajaBloqueoTO caja);
    public void updateCajaBloqueo(CajaBloqueoTO caja);
    public void deleteCajaBloqueo(CajaBloqueoTO caja);
    public List<CajaBloqueoTO> selectCajasBloqueos( Map<String,Object> params );
    /**/
    
    /*
     * Manipulación de Candados
     */
    public void insertCandado(CandadoTO candado);
    public void updateCandado(CandadoTO candado);
    public void deleteCandado(CandadoTO candado);
    public List<CandadoTO> selectCandados( Map<String,Object> params );
    /**/
    
    /*
     * Manipulación de Personas
     */
    public void insertPersona(PersonaTO persona);
    public void updatePersona(PersonaTO persona);
    public void deletePersona(PersonaTO persona);
    public List<PersonaTO> selectPersonas( Map<String,Object> params );
    /**/

    /*
     * Manipulación de Trabajadores
     */
    public void insertTrabajador(TrabajadorTO trabajador);
    public void updateTrabajador(TrabajadorTO trabajador);
    public void deleteTrabajador(TrabajadorTO trabajador);
    public List<TrabajadorTO> selectTrabajadores( Map<String,Object> params );
    /**/
}
