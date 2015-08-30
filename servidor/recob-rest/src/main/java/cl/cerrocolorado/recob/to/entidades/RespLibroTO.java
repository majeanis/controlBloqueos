package cl.cerrocolorado.recob.to.entidades;

import cl.cerrocolorado.recob.utils.EntidadTO;

import java.util.Date;

/**
 * @author mauricio.camara
 */
public class RespLibroTO extends EntidadTO
{
    private static final long serialVersionUID = 1L;

    private LibroBloqueoTO libro;
    private TrabajadorTO trabajador;
    private Date fechaIngreso;
    private Date fechaSalida;

    public LibroBloqueoTO getLibro()
    {
        return libro;
    }

    public void setLibro(LibroBloqueoTO libro)
    {
        this.libro = libro;
    }

    public TrabajadorTO getTrabajador()
    {
        return trabajador;
    }

    public void setTrabajador(TrabajadorTO trabajador)
    {
        this.trabajador = trabajador;
    }

    public Date getFechaIngreso()
    {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso)
    {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaSalida()
    {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida)
    {
        this.fechaSalida = fechaSalida;
    }

    @Override
    public boolean isKeyBlank()
    {
        return isIdBlank() && (libro == null || libro.isKeyBlank()) || (trabajador==null || trabajador.isKeyBlank());
    }
}
