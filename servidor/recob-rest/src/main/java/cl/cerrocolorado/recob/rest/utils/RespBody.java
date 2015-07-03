package cl.cerrocolorado.recob.rest.utils;

public class RespBody<T>
{
    private final T valor;
    
    public RespBody(T valor)
    {
        this.valor = valor;
    }
    
    public T get()
    {
        return this.valor;
    }
}
