package cl.cerrocolorado.recob.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/**
 *
 * @author mauricio.camara
 */
public class RutSerializer extends JsonSerializer<Rut>
{
    @Override
    public void serialize(Rut value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException
    {
        gen.writeString(value.toText());
    }
}
