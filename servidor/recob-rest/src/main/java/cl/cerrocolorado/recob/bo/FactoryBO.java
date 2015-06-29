package cl.cerrocolorado.recob.bo;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author mauricio.camara
 */
public class FactoryBO
{
    private final static BeanFactory beanFactory;

    static {
        beanFactory = new ClassPathXmlApplicationContext(
                "classpath:/cl/cerrocolorado/recob/bo/config/spring.*.xml");
    }

    public static CajaBloqueoBO getCajaBloqueoBO() {
        return (CajaBloqueoBO) beanFactory.getBean( "cajaBloqueoBO" );
    }
    
    public static CandadoBO getCandadoBO()
    {
        return (CandadoBO) beanFactory.getBean("candadoBO");
    }
    
    public static EmpresaBO getEmpresaBO()
    {
        return (EmpresaBO) beanFactory.getBean("empresaBO");
    }

    public static UbicacionBO getUbicacionBO()
    {
        return (UbicacionBO) beanFactory.getBean("ubicacionBO");
    }
}