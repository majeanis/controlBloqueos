package cl.cerrocolorado.recob.to;

import cl.cerrocolorado.recob.utils.EntidadTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author mauricio.camara
 */
public class LibroBloqueoTO extends EntidadTO
{
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private UbicacionTO ubicacion;
    
    private String id;
    private Integer numero;
    private Date fecha;
    private Boolean cerrado;
    private Date fechaCierre;
    private CajaBloqueoTO caja;

    public UbicacionTO getUbicacion()
    {
        return ubicacion;
    }

    public void setUbicacion(UbicacionTO ubicacion)
    {
        this.ubicacion = ubicacion;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
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
        return isIdBlank() && (numero == 0 || caja == null || caja.isKeyBlank());
    }
    
    @Override
    public boolean isIdBlank()
    {
        return StringUtils.isBlank(id);
    }

}
