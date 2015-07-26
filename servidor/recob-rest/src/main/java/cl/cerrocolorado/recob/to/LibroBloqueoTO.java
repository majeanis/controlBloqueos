package cl.cerrocolorado.recob.to;

import cl.cerrocolorado.recob.utils.EntidadTO;
import java.util.Date;

/**
 *
 * @author mauricio.camara
 */
public class LibroBloqueoTO extends EntidadTO
{

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer numero;
    private Date fecha;
    private Boolean cerrado;
    private Date fechaCierre;
    private CajaBloqueoTO caja;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getNumero()
    {
        return numero;
    }

    public void setNumero(Integer numero)
    {
        this.numero = numero;
    }

    public Date getFecha()
    {
        return fecha;
    }

    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    public Boolean getCerrado()
    {
        return cerrado;
    }

    public void setCerrado(Boolean cerrado)
    {
        this.cerrado = cerrado;
    }

    public Date getFechaCierre()
    {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre)
    {
        this.fechaCierre = fechaCierre;
    }

    public CajaBloqueoTO getCaja()
    {
        return caja;
    }

    public void setCaja(CajaBloqueoTO caja)
    {
        this.caja = caja;
    }

    @Override
    public boolean isKeyBlank()
    {
        return (id == null || id == 0) || (numero==null || numero == 0 || caja == null || caja.isKeyBlank());
    }
}
