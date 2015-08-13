package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.to.ResponsableTO;
import cl.cerrocolorado.recob.to.UbicacionTO;
import cl.cerrocolorado.recob.utils.Respuesta;


/**
 *
 * @author mauricio.camara
 */
public interface ResponsableBO extends BaseBO<ResponsableTO>
{
    public Respuesta<ResponsableTO> getVigente(UbicacionTO pk);
}
