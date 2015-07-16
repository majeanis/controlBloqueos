package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.to.EmpresaTO;
import cl.cerrocolorado.recob.to.PersonaTO;
import cl.cerrocolorado.recob.to.TrabajadorTO;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.Transaccional;

import java.util.List;

/**
 * @author mauricio.camara
 */
public interface EmpresaBO
{
	@Transaccional
    public Respuesta<EmpresaTO> guardar(EmpresaTO empresa) throws Exception;

	@Transaccional
    public Resultado eliminar(EmpresaTO pkEmpresa) throws Exception;

    public EmpresaTO get(EmpresaTO pkEmpresa);

    public List<EmpresaTO> getVigentes();
    
    public List<EmpresaTO> getTodos();
}
