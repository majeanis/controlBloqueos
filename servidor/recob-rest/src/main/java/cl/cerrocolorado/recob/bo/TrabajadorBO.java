package cl.cerrocolorado.recob.bo;

import java.util.List;

import cl.cerrocolorado.recob.to.EmpresaTO;
import cl.cerrocolorado.recob.to.TrabajadorTO;
import cl.cerrocolorado.recob.utils.Respuesta;
import java.util.Optional;

/**
 * @author mauricio.camara
 */
public interface TrabajadorBO extends BaseBO<TrabajadorTO>
{
    public Respuesta<List<TrabajadorTO>> getTrabajadores(Optional<Boolean> vigencia);

    public Respuesta<List<TrabajadorTO>> getTrabajadores(EmpresaTO pkEmpresa, Optional<Boolean> vigencia);
}
