package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.to.ResponsableTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.Resultado;
import cl.cerrocolorado.recob.utils.Transaccional;
import java.util.Date;

import java.util.List;

/**
 *
 * @author mauricio.camara
 */
public interface ResponsableBO
{
	@Transaccional
    public Respuesta<ResponsableTO> guardar(ResponsableTO responsable) throws Exception;

	@Transaccional
    public Resultado eliminar(ResponsableTO pk) throws Exception;

    public Respuesta<ResponsableTO> getVigente(UbicacionTO pk);
}
