package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.utils.BaseTO;
import cl.cerrocolorado.recob.utils.Respuesta;
import cl.cerrocolorado.recob.utils.Transaccional;

/**
 *
 * @author mauricio.camara
 */
public interface BaseBO<T extends BaseTO> 
{
	@Transaccional
    public Respuesta<T> save(T datos) throws Exception;

	@Transaccional
    public Respuesta<T> delete(T pk) throws Exception;

    public Respuesta<T> get(T pk);
}
