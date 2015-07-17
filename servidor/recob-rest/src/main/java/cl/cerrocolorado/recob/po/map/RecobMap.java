package cl.cerrocolorado.recob.po.map;

import cl.cerrocolorado.recob.to.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.CandadoTO;
import cl.cerrocolorado.recob.to.EmpresaTO;
import cl.cerrocolorado.recob.to.EquipoTO;
import cl.cerrocolorado.recob.to.PersonaTO;
import cl.cerrocolorado.recob.to.TagTO;
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
    public int  childsCajaBloqueo(CajaBloqueoTO caja);
    public List<CajaBloqueoTO> selectCajasBloqueos( Map<String,Object> parms );
    /**/
    
    /*
     * Manipulación de Candados
     */
    public void insertCandado(CandadoTO candado);
    public void updateCandado(CandadoTO candado);
    public void deleteCandado(CandadoTO candado);
    public int  childsCandado(CandadoTO candado);    
    public List<CandadoTO> selectCandados( Map<String,Object> parms );
    /**/
    
    /*
     * Manipulación de Personas
     */
    public void insertPersona(PersonaTO persona);
    public void updatePersona(PersonaTO persona);
    public void deletePersona(PersonaTO persona);
    public int  childsPersona(PersonaTO persona);
    public List<PersonaTO> selectPersonas( Map<String,Object> parms );
    /**/

    /*
     * Manipulación de Trabajadores
     */
    public void insertTrabajador(TrabajadorTO trabajador);
    public void updateTrabajador(TrabajadorTO trabajador);
    public void deleteTrabajador(TrabajadorTO trabajador);
    public int  childsTrabajador(TrabajadorTO trabajador);
    public List<TrabajadorTO> selectTrabajadores( Map<String,Object> parms );
    /**/

    /*
     * Manipulación de Empresas
     */
    public void insertEmpresa(EmpresaTO empresa);
    public void updateEmpresa(EmpresaTO empresa);
    public void deleteEmpresa(EmpresaTO empresa);
    public int  childsEmpresa(EmpresaTO empresa);
    public List<EmpresaTO> selectEmpresas( Map<String,Object> parms );
    /**/

    /*
     * Manipulación de Equipos
     */
    public void insertEquipo(EquipoTO equipo);
    public void updateEquipo(EquipoTO equipo);
    public void deleteEquipo(EquipoTO equipo);
    public int  childsEquipo(EquipoTO equipo);
    public List<EquipoTO> selectEquipos( Map<String,Object> parms );
    
    /* 
     * Manipulación de TAGs
     */
    public void insertTag(TagTO tag);
    public void updateTag(TagTO tag);
    public void deleteTag(TagTO tag);
    public int  childsTag(TagTO tag);
    public List<TagTO> selectTags( Map<String,Object> parms );
    
}
