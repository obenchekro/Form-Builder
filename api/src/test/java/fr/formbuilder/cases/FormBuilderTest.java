package fr.formbuilder.cases;

import fr.formbuilder.common.FormBuilderAssertionsTest;
import fr.formbuilder.controller.FormBuilderController;
import fr.formbuilder.dto.FormDefinitionResponse;
import fr.formbuilder.dto.GenerateFormsRequest;
import fr.formbuilder.models.ResponseData;
import fr.formbuilder.utils.DatasetLoader;
import org.junit.jupiter.api.Test;

import java.util.List;

import fr.formbuilder.common.TestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FormBuilderTest extends FormBuilderAssertionsTest {
    private final FormBuilderController formBuilderController;

    @Autowired
    public FormBuilderTest(FormBuilderController formBuilderController) {
        this.formBuilderController = formBuilderController;
    }

    @Test
    public void testCaseAllFormBuilder() {
        List<TestCase<GenerateFormsRequest>> cases = List.of(
            TestCase.<GenerateFormsRequest>builder()
                .name("Form Builder containing only one valid step")
                .data(DatasetLoader.loadFormReqData("form-builder-request-one-step.json"))
                .assertFn(this::oneFormValidAssertions)
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
}
