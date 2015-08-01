package cl.cerrocolorado.recob.po;

/**
 *
 * @author mauricio.camara
 * @param <T>
 */
public interface BasePO<T>
{
    public void eliminar(T pk);
    public boolean esEliminable(T pk);
    public T get(T pk);
    public T guardar(T datos);
}
