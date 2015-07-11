package cl.cerrocolorado.recob.utils;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtils 
{
	private static final ObjectMapper mapper;;
	
	static
	{
		mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
	}

	public static String toJsonString(Object obj)
	{
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
	
	public static <T> T fromJsonString(String json, Class<T> clazz)
	{
		try {
			return mapper.readValue(json, clazz);
		} catch (Exception e) {
			return null;
		}
	}
}
