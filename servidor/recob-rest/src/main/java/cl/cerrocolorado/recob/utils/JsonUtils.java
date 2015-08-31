package cl.cerrocolorado.recob.utils;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import java.io.InputStream;

public class JsonUtils 
{
	private static final ObjectMapper mapper;;
	
	static
	{
        SimpleModule sm = new SimpleModule();
        sm.addSerializer(Rut.class, new RutSerializer());
        
		mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"));
		mapper.registerModule(new JSR310Module());
    	mapper.registerModule(new Jdk8Module());
        mapper.registerModule(sm);
	}

	public static String toJsonString(Object obj)
	{
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
            System.out.println(e);
			return null;
		}
	}
	
	public static <T> T fromJson(String json, Class<T> clazz)
	{
		try {
			return mapper.readValue(json, clazz);
		} catch (Exception e) {
			return null;
		}
	}

  	public static <T> T fromJson(InputStream is, Class<T> clazz)
	{
		try {
			return mapper.readValue(is, clazz);
		} catch (Exception e) {
			return null;
		}
	}

    public static ObjectMapper getMapper()
    {
        return mapper;
    }
}
