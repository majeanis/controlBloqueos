package cl.cerrocolorado.recob.bo.utils;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;

/**
 *
 * @author mauricio.camara
 */
public class BeansFactory
{
    private final static BeanFactory beanFactory;

    static {
        beanFactory = new ClassPathXmlApplicationContext(
                "classpath:/cl/cerrocolorado/recob/bo/config/spring.*.xml");
    }

    public static Object getBean(String nombre)
    {
        return beanFactory.getBean(nombre);
    }

    public static PlatformTransactionManager getTransactionManager()
    {
        return (PlatformTransactionManager) beanFactory.getBean("txManager");
    }
}
