package fr.formbuilder.convertor.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import fr.formbuilder.convertor.DefaultValueSerializer;
import lombok.SneakyThrows;

public class TitleSerializer extends DefaultValueSerializer {
    public TitleSerializer() {
        super(null);
    }

    @Override
    @SneakyThrows
    public void serialize(String value, JsonGenerator generator, SerializerProvider serializers) {
        String title = (value == null || value.isBlank()) ? "#TITLE#" : value;
        generator.writeString(title);
    }
}