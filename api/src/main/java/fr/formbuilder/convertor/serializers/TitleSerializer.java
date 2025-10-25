package fr.formbuilder.convertor.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import fr.formbuilder.convertor.DefaultValueSerializer;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.SneakyThrows;

public class TitleSerializer extends DefaultValueSerializer {
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    public TitleSerializer() {
        super(null);
    }

    @Override
    @SneakyThrows
    public void serialize(String value, JsonGenerator generator, SerializerProvider serializers) {
        int index = COUNTER.incrementAndGet();
        String title = (value == null || value.isBlank()) ? String.format("#TITLE_%s#", index) : value;
        generator.writeString(title);
    }
}