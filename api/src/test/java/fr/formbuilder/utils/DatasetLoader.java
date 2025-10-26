package fr.formbuilder.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.formbuilder.dto.FormDefinitionResponse;
import fr.formbuilder.dto.GenerateFormsRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DatasetLoader {
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @SneakyThrows
    public static <T> T loadDataSet(String path, Class<T> modelClass) {
        try (InputStream inputStream = DatasetLoader.class.getClassLoader().getResourceAsStream(path)) {
            if (Objects.isNull(inputStream)) throw new IllegalArgumentException("Resource not found: " + path);
            return MAPPER.readValue(inputStream, modelClass);
        } catch (IOException e) {
            throw new IOException("Cannot load dataset test file: " + path, e);
        }
    }

    @SneakyThrows
    public static GenerateFormsRequest loadFormReqData(String formReqFile) {
        return loadDataSet(String.format("static/requests/%s", formReqFile), GenerateFormsRequest.class);
    }

    @SneakyThrows
    public static FormDefinitionResponse loadFormDefinitionData(String formDefFile) {
        return loadDataSet(String.format("static/responses/%s", formDefFile), FormDefinitionResponse.class);
    }
}
