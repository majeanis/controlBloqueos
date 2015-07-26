package cl.cerrocolorado.recob.bo;

import java.util.List;

import cl.cerrocolorado.recob.to.EmpresaTO;
import cl.cerrocolorado.recob.to.TrabajadorTO;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.Transaccional;
import java.util.Optional;

/**
 * @author mauricio.camara
 */
public interface TrabajadorBO
{
	@Transaccional
    public Respuesta<TrabajadorTO> guardar(TrabajadorTO trabajador) throws Exception;

	@Transaccional
    public Resultado eliminar(TrabajadorTO pkTrabajador) throws Exception;

    public Respuesta<TrabajadorTO> get(TrabajadorTO pkTrabajador);

    public Respuesta<List<TrabajadorTO>> getTodos(Optional<Boolean> vigencia);

    public Respuesta<List<TrabajadorTO>> getTodos(EmpresaTO pkEmpresa, 
                                                  Optional<Boolean> vigencia);

    public Respuesta<List<TrabajadorTO>> getVigentes();
    public Respuesta<List<TrabajadorTO>> getVigentes(EmpresaTO pkEmpresa);
    
}
