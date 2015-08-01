package cl.cerrocolorado.recob.bo.utils;

import cl.cerrocolorado.recob.bo.CajaBloqueoBO;
import cl.cerrocolorado.recob.bo.CandadoBO;
import cl.cerrocolorado.recob.bo.EmpresaBO;
import cl.cerrocolorado.recob.bo.EquipoBO;
import cl.cerrocolorado.recob.bo.LibroBloqueoBO;
import cl.cerrocolorado.recob.bo.TrabajadorBO;
import cl.cerrocolorado.recob.bo.UbicacionBO;

/**
 *
 * @author mauricio.camara
 */
public class FactoryBO
{
    public static CajaBloqueoBO getCajaBloqueoBO() {
        return (CajaBloqueoBO) BeansFactory.getBean( "cajaBloqueoBO" );
    }
    
    public static CandadoBO getCandadoBO()
    {
        return (CandadoBO) BeansFactory.getBean("candadoBO");
    }
    
    public static EmpresaBO getEmpresaBO()
    {
        return (EmpresaBO) BeansFactory.getBean("empresaBO");
    }

    public static TrabajadorBO getTrabajadorBO()
    {
        return (TrabajadorBO) BeansFactory.getBean("trabajadorBO");
    }

    public static UbicacionBO getUbicacionBO()
    {
        return (UbicacionBO) BeansFactory.getBean("ubicacionBO");
    }

    public static EquipoBO getEquipoBO()
    {
        return (EquipoBO) BeansFactory.getBean("equipoBO");
    }

    public static LibroBloqueoBO getLibroBloqueoBO()
    {
        return (LibroBloqueoBO) BeansFactory.getBean("libroBloqueoBO");
    }

}