package cl.cerrocolorado.recob.utils;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Configuracion
{
    private static final Logger             logger = LogManager.getLogger(Configuracion.class);
    private static final String             properties = "recob.properties";
    private static PropertiesConfiguration  config;

    private static Configuration getConfiguracion() throws ConfigurationException
    {
        logger.trace("getConfiguracion[INI]");
        
        if (config != null)
        {
            logger.trace( "getConfiguracion[F01] reutilización instance de \"config\"" );
            return config;
        }
        
        synchronized (Configuration.class)
        {
            if (config != null)
            {
                logger.trace( "getConfiguracion[F02] reutilización instance de \"config\"" );
                return config;
            }

            config = new PropertiesConfiguration(properties);
            config.setReloadingStrategy(new FileChangedReloadingStrategy());

            logger.trace( "getConfiguracion[FIN] instanciación de \"config\": {}", properties );
            return config;
        }
    }
    
    public static String getPathTemporal() 
    {
        String value = System.getProperty("java.io.tmpdir");
        return value;
    }
    
    @Override
    public String toString()
    {
        if(config != null)
            return "[properies:" + config.getPath() + "]";

        return "[properties:" + properties + "]";
    }
}
