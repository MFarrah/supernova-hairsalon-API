// DurationJsonHelper.java
package nl.mfarr.supernova.helpers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Duration;

public class DurationJsonHelper {

    // Serializer that outputs Duration in minutes
    public static class DurationSerializer extends JsonSerializer<Duration> {
        @Override
        public void serialize(Duration duration, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            // Output Duration in minutes
            gen.writeNumber(duration.toMinutes());
        }
    }

    // Deserializer that handles both numeric (minutes) and ISO-8601 format
    public static class DurationDeserializer extends JsonDeserializer<Duration> {
        @Override
        public Duration deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String text = p.getText();
            if (text.matches("\\d+")) {
                // If it's a numeric value, treat it as minutes
                return Duration.ofMinutes(Long.parseLong(text));
            }
            // Otherwise, treat it as ISO-8601 formatted string
            return Duration.parse(text);
        }
    }
}