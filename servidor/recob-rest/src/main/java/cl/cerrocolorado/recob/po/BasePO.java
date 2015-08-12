package cl.cerrocolorado.recob.po;

/**
 *
 * @author mauricio.camara
 * @param <T>
 */
public interface BasePO<T>
{
    public T insert(T datos);
    public T update(T datos);
    public void delete(T pk);
    public T get(T pk);
    public boolean isDeleteable(T pk);
}
