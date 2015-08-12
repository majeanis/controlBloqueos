package cl.cerrocolorado.recob.po;

/**
 *
 * @author mauricio.camara
 * @param <T>
 */
public interface BasePO<T>
{
    public T crear(T datos);
    public T obtener(T pk);
    public T modificar(T datos);
    public void eliminar(T pk);
    public boolean esEliminable(T pk);
}
