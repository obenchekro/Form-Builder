package fr.formbuilder.common;

import fr.formbuilder.dto.FormDefinitionResponse;
import fr.formbuilder.models.ResponseData;
import org.junit.jupiter.api.Assertions;

public class FormBuilderAssertionsTest {
     protected <T> void assertFormBuilderResponse(ResponseData<T> expectedResponse, ResponseData<T> actualResponse) {
        Assertions.assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        Assertions.assertEquals(expectedResponse.getState(), actualResponse.getState());
        Assertions.assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        Assertions.assertEquals(expectedResponse.getData(), actualResponse.getData());
    }

    protected void assertFormBuilderConditionsVerified(FormDefinitionResponse expectedFormDefResponse, FormDefinitionResponse actualFormDefResponse) {}
}
