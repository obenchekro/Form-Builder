package fr.formbuilder.controller;

import fr.formbuilder.dto.GenerateFormsRequest;
import fr.formbuilder.dto.FormDefinitionResponse;
import fr.formbuilder.enums.FieldType;
import fr.formbuilder.exception.InvalidFormDefinitionException;
import fr.formbuilder.models.ResponseData;
import fr.formbuilder.service.FormBuilderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/form-builder")
@RequiredArgsConstructor
public class FormBuilderController {
    private final FormBuilderService formBuilderService;

    @GetMapping("/field-types")
    public ResponseEntity<ResponseData<FieldType[]>> retrieveFieldTypes() {
        return ResponseEntity.ok(ResponseData.success(FieldType.values()));
    }

    @PostMapping("/construct")
    public ResponseEntity<ResponseData<FormDefinitionResponse>> constructForms(@Valid @RequestBody GenerateFormsRequest request) throws InvalidFormDefinitionException {
        FormDefinitionResponse formBuilder = formBuilderService.prepareFormRequest(request);
        return ResponseEntity.ok(ResponseData.success("Form generated successfully", formBuilder));
    }
}
