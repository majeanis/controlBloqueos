package cl.cerrocolorado.recob.utils;

import java.util.ArrayList;
import java.util.List;

public class ResultadoProceso 
    implements Resultado {

    private static final long   serialVersionUID = 1L;

    private boolean             hayExceptions;
    private Severidad           severidad;
    private final List<Mensaje> mensajes;
    
    public ResultadoProceso() {
        this.mensajes = new ArrayList<>();
        this.severidad = Severidad.OK;
        this.hayExceptions = false;        
    }

    public ResultadoProceso(Resultado another) {
        this.severidad = another.getSeveridad();
        this.mensajes  = new ArrayList<>();
        this.mensajes.addAll(another.getMensajes());
        this.hayExceptions = another.hayExceptions();
    }
    
    @Override
    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    @Override
    public Severidad getSeveridad() {
        return severidad;
    }

    @Override
    public boolean hayExceptions() {
        return hayExceptions;
    }

    private void refreshSeveridad(Mensaje mensaje) {
        if (mensaje.getSeveridad().getId() > severidad.getId()) {
            severidad = mensaje.getSeveridad();
        }
    }

    private void refreshTieneExceptions(Mensaje mensaje) {
        if( hayExceptions ) return;
        if( mensaje.getException() != null ) hayExceptions = true;
    }

    @Override
    public Mensaje addMensaje(Mensaje mensaje) {
        mensajes.add(mensaje);
        refreshSeveridad(mensaje);
        refreshTieneExceptions(mensaje);
        
        return mensaje;
    }

    @Override
    public String toString() {
        return ToStringUtils.toString(this);
    }

    @Override
    public Mensaje addException(Class<?> clazz, Exception exception)
    {
        return this.addMensaje( new MensajeError(clazz, exception) );
    }

    @Override
    public Mensaje addError(Class<?> clazz, String textoBase, String... valores)
    {
        return this.addMensaje( new MensajeError( clazz, textoBase, valores) );
    }

    @Override
    public Mensaje addMensaje(Class<?> clazz, String textoBase, String... valores)
    {
        return this.addMensaje( new MensajeInfo( clazz, textoBase, valores) );
    }
}