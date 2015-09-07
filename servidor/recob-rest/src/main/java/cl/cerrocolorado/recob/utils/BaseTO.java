package cl.cerrocolorado.recob.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseTO implements ObjetoTO
{
    private static final long serialVersionUID = 1L;
    protected static final Logger logger = LogManager.getLogger(BaseTO.class);
    
    @Override
    public String toString()
    {
        return ToStringUtils.toString(this);
    }

    @Override
    public boolean equals(Object obj)
    {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public BaseTO clone()
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(this);
            return mapper.readValue(json, this.getClass());
        }
        catch (IOException ex)
        {
            logger.error( String.format( "clone[ERR] al clonar objeto: %s", this), ex);
            return null;
        }
    }
}
