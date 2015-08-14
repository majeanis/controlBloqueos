package cl.cerrocolorado.recob.bo;

import java.util.List;

import cl.cerrocolorado.recob.to.EmpresaTO;
import cl.cerrocolorado.recob.utils.Respuesta;
import java.util.Optional;

/**
 * @author mauricio.camara
 */
public interface EmpresaBO extends BaseBO<EmpresaTO>
{
    public Respuesta<List<EmpresaTO>> getTodos(Optional<Boolean> vigencia);
}
