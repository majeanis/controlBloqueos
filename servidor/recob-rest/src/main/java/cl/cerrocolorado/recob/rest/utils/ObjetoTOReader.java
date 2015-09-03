package cl.cerrocolorado.recob.rest.utils;

import cl.cerrocolorado.recob.utils.JsonUtils;
import cl.cerrocolorado.recob.utils.ObjetoTO;
import cl.cerrocolorado.recob.utils.Rut;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author mauricio.camara
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class ObjetoTOReader implements MessageBodyReader<ObjetoTO>
{
    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
    {
        return ObjetoTO.class.isAssignableFrom(type);
    }

    @Override
    public ObjetoTO readFrom(Class<ObjetoTO> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException
    {
        return JsonUtils.fromJson(entityStream, type);
    }
}
