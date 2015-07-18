package cl.cerrocolorado.recob.utils;

import java.io.Serializable;
import java.util.List;

public interface Resultado extends Serializable
{
    default boolean esExitoso()
    {
        Severidad severidad = getSeveridad();
        return (severidad == Severidad.OK || severidad == Severidad.INFO);
    }

    public boolean hayExceptions();

    public Severidad getSeveridad();

    public List<Mensaje> getMensajes();

    public Mensaje addException(Class<?> clazz, Exception exception);
    
    public Mensaje addError(Class<?> clazz, String textoBase, Object...valores);
    
    public Mensaje addMensaje(Class<?> clazz, String textoBase, Object...valores);

    public Mensaje addMensaje(Mensaje mensaje);

    default void append(Resultado another, String addText)
    {
    	if(another==null)
    		return;
    	
    	for( Mensaje m: another.getMensajes() )
    	{
    		Mensaje nuevo = new Mensaje(m.getSeveridad(), m.getCodigo(), m.getTexto() + addText );
    		this.addMensaje(nuevo);
    	}
    }

    default void append(Resultado another)
    {
    	append(another, "");
    }
}
