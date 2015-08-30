package cl.cerrocolorado.recob.to.entidades;

import java.util.Date;

import cl.cerrocolorado.recob.utils.EntidadTO;

/**
 * @author mauricio.camara
 */
public class DotacionLibroTO extends EntidadTO
{
    private static final long serialVersionUID = 1L;

    private LibroBloqueoTO libro;
    private FuncionBloqueoTO funcion;
    private TrabajadorTO trabajador;
    private CandadoTO candado;
    private PersonaTO autorizaBloqueo;
    private PersonaTO autorizaDesbloqueo;
    private Date fechaBloqueo;
    private Date fechaDesbloqueo;

    public LibroBloqueoTO getLibro()
    {
        return libro;
    }

    public void setLibro(LibroBloqueoTO libro)
    {
        this.libro = libro;
    }

    public FuncionBloqueoTO getFuncion()
    {
        return funcion;
    }

    public void setFuncion(FuncionBloqueoTO funcion)
    {
        this.funcion = funcion;
    }

    public TrabajadorTO getTrabajador()
    {
        return trabajador;
    }

    public void setTrabajador(TrabajadorTO trabajador)
    {
        this.trabajador = trabajador;
    }

    public CandadoTO getCandado()
    {
        return candado;
    }

    public void setCandado(CandadoTO candado)
    {
        this.candado = candado;
    }

    public PersonaTO getAutorizaBloqueo()
    {
        return autorizaBloqueo;
    }

    public void setAutorizaBloqueo(PersonaTO autorizaBloqueo)
    {
        this.autorizaBloqueo = autorizaBloqueo;
    }

    public PersonaTO getAutorizaDesbloqueo()
    {
        return autorizaDesbloqueo;
    }

    public void setAutorizaDesbloqueo(PersonaTO autorizaDesbloqueo)
    {
        this.autorizaDesbloqueo = autorizaDesbloqueo;
    }

    public Date getFechaBloqueo()
    {
        return fechaBloqueo;
    }

    public void setFechaBloqueo(Date fechaBloqueo)
    {
        this.fechaBloqueo = fechaBloqueo;
    }

    public Date getFechaDesbloqueo()
    {
        return fechaDesbloqueo;
    }

    public void setFechaDesbloqueo(Date fechaDesbloqueo)
    {
        this.fechaDesbloqueo = fechaDesbloqueo;
    }

    @Override
    public boolean isKeyBlank()
    {
        return isIdBlank() && (libro == null || libro.isKeyBlank() || 
                               funcion == null || funcion.isKeyBlank() || 
                               trabajador == null || trabajador.isKeyBlank());
    }
}
