package cl.cerrocolorado.recob.bo;

import java.util.List;

import cl.cerrocolorado.recob.to.EmpresaTO;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.Transaccional;

/**
 * @author mauricio.camara
 */
public interface EmpresaBO
{
	@Transaccional
    public Respuesta<EmpresaTO> guardar(EmpresaTO empresa) throws Exception;

	@Transaccional
    public Resultado eliminar(EmpresaTO pkEmpresa) throws Exception;

    public Respuesta<EmpresaTO> get(EmpresaTO pkEmpresa);
        
    public Respuesta<List<EmpresaTO>> getTodos(Boolean vigencia);

    public Respuesta<List<EmpresaTO>> getVigentes();
}
