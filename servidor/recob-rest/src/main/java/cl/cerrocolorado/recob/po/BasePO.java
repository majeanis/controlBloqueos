package cl.cerrocolorado.recob.po;

/**
 *
 * @author mauricio.camara
 * @param <T>
 */
public interface BasePO<T>
{
    public T guardar(T datos);
    public T obtener(T pk);
    public void eliminar(T pk);
    public boolean esEliminable(T pk);
}
