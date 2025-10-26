package fr.formbuilder.common;

import fr.formbuilder.dto.FormDefinitionResponse;
import fr.formbuilder.models.ResponseData;
import org.junit.jupiter.api.Assertions;

import java.util.stream.IntStream;

public class FormBuilderAssertionsTest {
     protected <T> void assertFormBuilderResponse(ResponseData<T> expectedResponse, ResponseData<T> actualResponse) {
        Assertions.assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        Assertions.assertEquals(expectedResponse.getState(), actualResponse.getState());
        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(expectedResponse.getData(), actualResponse.getData());
        Assertions.assertEquals(expectedResponse.getDetails(), actualResponse.getDetails());
    }

    protected void assertFormBuilderConditionsVerified(FormDefinitionResponse expectedFormDefResponse, FormDefinitionResponse actualFormDefResponse) {
        Assertions.assertEquals(expectedFormDefResponse.getSteps().size(), actualFormDefResponse.getSteps().size(), "Mismatch in step count");
        IntStream.range(0, expectedFormDefResponse.getSteps().size()).forEach(i ->
            IntStream.range(0, expectedFormDefResponse.getSteps().get(i).getFields().size()).forEach(j ->
                Assertions.assertEquals(
                    expectedFormDefResponse.getSteps().get(i).getFields().get(j),
                    actualFormDefResponse.getSteps().get(i).getFields().get(j),
                    String.format("Field mismatch at step %d, field %d", i, j)
                )
            )
        );
    }
}
