package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.to.EmpresaTO;
import cl.cerrocolorado.recob.utils.Resultado;
import java.util.List;

/**
 * @author mauricio.camara
 */
public interface EmpresaBO
{
    public Respuesta<EmpresaTO> guardar(EmpresaTO empresa);

    public Resultado eliminar(EmpresaTO pkEmpresa);

    public EmpresaTO get(EmpresaTO pkEmpresa);

    public List<EmpresaTO> getVigentes();
    
    public List<EmpresaTO> getTodos();
}
