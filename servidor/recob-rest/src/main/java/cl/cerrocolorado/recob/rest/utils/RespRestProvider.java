package cl.cerrocolorado.recob.rest.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import cl.cerrocolorado.recob.utils.JsonUtils;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class RespRestProvider implements MessageBodyWriter<RespRest<?>> 
{
	@Override
	public long getSize(RespRest<?> arg0, Class<?> arg1, Type arg2, Annotation[] arg3, MediaType arg4) {
		return 0;
	}

	@Override
	public boolean isWriteable(Class<?> arg0, Type arg1, Annotation[] arg2, MediaType arg3) {
		return arg0 == RespRest.class;
	}

	@Override
	public void writeTo(RespRest<?> arg0, Class<?> arg1, Type arg2, Annotation[] arg3, MediaType arg4,
			MultivaluedMap<String, Object> arg5, OutputStream arg6) throws IOException, WebApplicationException {
		String json = JsonUtils.toJsonString(arg0);
        if( json != null )
            arg6.write(json.getBytes("UTF-8"));
	}
}
