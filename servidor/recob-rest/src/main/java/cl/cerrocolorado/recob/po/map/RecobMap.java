package cl.cerrocolorado.recob.po.map;

import cl.cerrocolorado.recob.to.entidades.CajaBloqueoTO;
import cl.cerrocolorado.recob.to.entidades.CandadoTO;
import cl.cerrocolorado.recob.to.entidades.DotacionLibroTO;
import cl.cerrocolorado.recob.to.entidades.EmpresaTO;
import cl.cerrocolorado.recob.to.entidades.EnergiaLibroTO;
import cl.cerrocolorado.recob.to.entidades.EquipoTO;
import cl.cerrocolorado.recob.to.entidades.FuncionBloqueoTO;
import cl.cerrocolorado.recob.to.entidades.LibroBloqueoTO;
import cl.cerrocolorado.recob.to.entidades.PersonaTO;
import cl.cerrocolorado.recob.to.entidades.RespLibroTO;
import cl.cerrocolorado.recob.to.entidades.ResponsableTO;
import cl.cerrocolorado.recob.to.entidades.TagLibroTO;
import cl.cerrocolorado.recob.to.entidades.TagTO;
import cl.cerrocolorado.recob.to.entidades.TrabajadorTO;
import cl.cerrocolorado.recob.to.entidades.UbicacionTO;
import cl.cerrocolorado.recob.to.entidades.UsoCandadoTO;
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
    public long  childsCajaBloqueo(CajaBloqueoTO caja);
    public List<CajaBloqueoTO> selectCajasBloqueos( Map<String,Object> parms );
    /**/
    
    /*
     * Manipulación de Candados
     */
    public void insertCandado(CandadoTO candado);
    public void updateCandado(CandadoTO candado);
    public void deleteCandado(CandadoTO candado);
    public long childsCandado(CandadoTO candado);    
    public List<CandadoTO> selectCandados( Map<String,Object> parms );
    public List<UsoCandadoTO> selectUsosCandado( Map<String,Object> parms);
    /**/
    
    /*
     * Manipulación de Personas
     */
    public void insertPersona(PersonaTO persona);
    public void updatePersona(PersonaTO persona);
    public void deletePersona(PersonaTO persona);
    public long childsPersona(PersonaTO persona);
    public List<PersonaTO> selectPersonas( Map<String,Object> parms );
    /**/

    /*
     * Manipulación de Trabajadores
     */
    public void insertTrabajador(TrabajadorTO trabajador);
    public void updateTrabajador(TrabajadorTO trabajador);
    public void deleteTrabajador(TrabajadorTO trabajador);
    public long childsTrabajador(TrabajadorTO trabajador);
    public List<TrabajadorTO> selectTrabajadores( Map<String,Object> parms );
    /**/

    /*
     * Manipulación de Empresas
     */
    public void insertEmpresa(EmpresaTO empresa);
    public void updateEmpresa(EmpresaTO empresa);
    public void deleteEmpresa(EmpresaTO empresa);
    public long childsEmpresa(EmpresaTO empresa);
    public List<EmpresaTO> selectEmpresas( Map<String,Object> parms );
    /**/

    /*
     * Manipulación de Equipos
     */
    public void insertEquipo(EquipoTO equipo);
    public void updateEquipo(EquipoTO equipo);
    public void deleteEquipo(EquipoTO equipo);
    public long childsEquipo(EquipoTO equipo);
    public List<EquipoTO> selectEquipos( Map<String,Object> parms );
    
    /* 
     * Manipulación de TAGs
     */
    public void insertTag(TagTO tag);
    public void updateTag(TagTO tag);
    public void deleteTag(TagTO tag);
    public long childsTag(TagTO tag);
    public List<TagTO> selectTags( Map<String,Object> parms );
    public int  deleteTags(EquipoTO equipo);
 
    /*
     * Manipulación de Responsables
     */
    public void insertResponsable(ResponsableTO responsable);
    public void updateResponsable(ResponsableTO responsable);
    public void deleteResponsable(ResponsableTO responsable);
    public long childsResponsable(ResponsableTO responsable);
    public List<ResponsableTO> selectResponsables(Map<String,Object> parms );

    /*
     * Manipulación de Libros de Bloqueos
     */
    public void insertLibro(LibroBloqueoTO libro);
    public void updateLibro(LibroBloqueoTO libro);
    public void deleteLibro(LibroBloqueoTO libro);
    public long childsLibro(LibroBloqueoTO libro);
    public int  selectNumeroLibro();
    public List<LibroBloqueoTO> selectLibros(Map<String,Object> parms);
    
    /* 
     * Manipulación de TAGs en Libros de Bloqueo
     */
    public void insertTagLibro(TagLibroTO tag);
    public void updateTagLibro(TagLibroTO tag);
    public void deleteTagLibro(TagLibroTO tag);
    public List<TagLibroTO> selectTagsLibro(Map<String,Object> parms);

    /*
     * Manipulación de Energías en el Libro
     */
    public void insertEnergiaLibro(EnergiaLibroTO energia);
    public void deleteEnergiaLibro(EnergiaLibroTO energia);
    public List<EnergiaLibroTO> selectEnergiasLibro(Map<String,Object> parms);

    /*
     * Manipulación de Dotación en el Libro
     */
    public void insertDotacionLibro(DotacionLibroTO dotacion);
    public void updateDotacionLibro(DotacionLibroTO dotacion);
    public void deleteDotacionLibro(DotacionLibroTO dotacion);
    public List<DotacionLibroTO> selectDotacionLibro(Map<String,Object> parms);

    /*
     * Manipulación de Responsables en el Libro
     */
    public void insertRespLibro(RespLibroTO responsable);
    public void updateRespLibro(RespLibroTO responsable);
    public List<RespLibroTO> selectRespLibro(Map<String,Object> parms);

    /*
     * Manipulación de Funciones de Bloqueo
     */
    public List<FuncionBloqueoTO> selectFuncionesBloqueo(Map<String,Object> parms);
}
