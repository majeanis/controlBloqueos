package cl.cerrocolorado.recob.po;

/**
 *
 * @author mauricio.camara
 * @param <T>
 */
public interface BasePO<T>
{
    public T save(T datos);
    public T get(T pk);
    public void delete(T pk);
    public boolean isDeleteable(T pk);
}
