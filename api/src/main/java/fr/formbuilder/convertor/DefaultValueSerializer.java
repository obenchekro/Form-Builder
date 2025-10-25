package fr.formbuilder.convertor;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.SneakyThrows;

public class DefaultValueSerializer extends JsonSerializer<String> {
    protected final String defaultValue;

    public DefaultValueSerializer(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    @SneakyThrows
    public void serialize(String value, JsonGenerator generator, SerializerProvider serializers) {
        String output = (value == null || value.isBlank()) ? defaultValue : value;
        generator.writeString(output);
    }
}
