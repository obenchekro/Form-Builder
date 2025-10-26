package fr.formbuilder.cases;

import fr.formbuilder.common.FormBuilderAssertionsTest;
import fr.formbuilder.controller.FormBuilderController;
import fr.formbuilder.dto.FormDefinitionResponse;
import fr.formbuilder.dto.GenerateFormsRequest;
import fr.formbuilder.exception.InvalidFormDefinitionException;
import fr.formbuilder.models.ResponseData;
import fr.formbuilder.utils.DatasetLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import fr.formbuilder.common.TestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FormBuilderTest extends FormBuilderAssertionsTest {
    private final FormBuilderController formBuilderController;
    private final TestRestTemplate restTemplate;

    private static final String FORM_BUILDER_CONSTRUCTION_ENDPOINT = "/form-builder/construct";

    @Autowired
    public FormBuilderTest(FormBuilderController formBuilderController, TestRestTemplate restTemplate) {
        this.formBuilderController = formBuilderController;
        this.restTemplate = restTemplate;
    }

    @Test
    public void testCaseAllFormBuilder() {
        List<TestCase<GenerateFormsRequest>> cases = List.of(
            TestCase.<GenerateFormsRequest>builder()
                .name("Form Builder containing only one valid step")
                .data(DatasetLoader.loadFormReqData("form-builder-request-one-step.json"))
                .assertFn(this::oneFormValidAssertions)
                .build(),
            TestCase.<GenerateFormsRequest>builder()
                .name("Form Builder containing many valid steps")
                .data(DatasetLoader.loadFormReqData("form-builder-request-many-steps.json"))
                .assertFn(this::oneFormWithManyValidStepsAssertions)
                .build(),
            TestCase.<GenerateFormsRequest>builder()
                .name("Form Builder with wrong fields count")
                .data(DatasetLoader.loadFormReqData("form-builder-request-wrong-count-one-step.json"))
                .assertFn(this::oneFormWithWrongFieldsCountAssertions)
                .build(),
            TestCase.<GenerateFormsRequest>builder()
                .name("Form Builder with validation failed")
                .data(DatasetLoader.loadFormReqData("form-builder-request-validation-failed.json"))
                .assertFn(this::oneFormWithValidationFailedAssertions)
                .build()
        );
        TestCase.assertAll("Form Builder – All Test Cases", cases);
    }

    public void oneFormValidAssertions(GenerateFormsRequest formRequest) {
        ResponseData<FormDefinitionResponse> expectedFormResponse = ResponseData.success(
            "Form generated successfully",
            DatasetLoader.loadFormDefinitionData("form-builder-response-one-step.json")
        );
        ResponseData<FormDefinitionResponse> actualFormResponse = formBuilderController.constructForms(formRequest).getBody();

        assertFormBuilderResponse(expectedFormResponse, actualFormResponse);
        assertFormBuilderConditionsVerified(expectedFormResponse.getData(), actualFormResponse.getData());
    }

    public void oneFormWithManyValidStepsAssertions(GenerateFormsRequest formRequest) {
        ResponseData<FormDefinitionResponse> expectedFormResponse = ResponseData.success(
            "Form generated successfully",
            DatasetLoader.loadFormDefinitionData("form-builder-response-many-steps.json")
        );
        ResponseData<FormDefinitionResponse> actualFormResponse = formBuilderController.constructForms(formRequest).getBody();

        assertFormBuilderResponse(expectedFormResponse, actualFormResponse);
        assertFormBuilderConditionsVerified(expectedFormResponse.getData(), actualFormResponse.getData());
    }

    public void oneFormWithWrongFieldsCountAssertions(GenerateFormsRequest formRequest) {
        ResponseData<FormDefinitionResponse> expectedFormResponse = ResponseData.error(
                422,
                "Invalid form configuration",
                List.of(
                    ResponseData.ErrorDetails.builder()
                        .message("Fields number is incorrect.")
                        .build()
                )
        );
        ResponseData<FormDefinitionResponse> actualFormResponse = restTemplate.postForEntity(FORM_BUILDER_CONSTRUCTION_ENDPOINT, formRequest, ResponseData.class).getBody();

        assertFormBuilderResponse(expectedFormResponse, actualFormResponse);
        Assertions.assertThrows(InvalidFormDefinitionException.class, () -> formBuilderController.constructForms(formRequest));
    }

    public void oneFormWithValidationFailedAssertions(GenerateFormsRequest formRequest) {
        ResponseData<FormDefinitionResponse> expectedFormResponse = ResponseData.error(
                400,
                "Validation failed",
                List.of(
                    ResponseData.ErrorDetails.builder()
                        .field("count")
                        .message("doit être supérieur ou égal à 1")
                        .build(),
                    ResponseData.ErrorDetails.builder()
                        .field("steps")
                        .message("ne doit pas être vide")
                        .build()
                )
        );
        ResponseData<FormDefinitionResponse> actualFormResponse = restTemplate.postForEntity(FORM_BUILDER_CONSTRUCTION_ENDPOINT, formRequest, ResponseData.class).getBody();

        assertFormBuilderResponse(expectedFormResponse, actualFormResponse);
    }
}
