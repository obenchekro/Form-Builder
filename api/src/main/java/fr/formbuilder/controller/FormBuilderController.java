package fr.formbuilder.controller;

import fr.formbuilder.dto.GenerateFormsRequest;
import fr.formbuilder.dto.FormDefinitionResponse;
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
    @PostMapping("/construct")
    public ResponseEntity<ResponseData<FormDefinitionResponse>> constructForms(@Valid @RequestBody GenerateFormsRequest request) throws InvalidFormDefinitionException {
        FormDefinitionResponse formBuilder = formBuilderService.prepareFormRequest(request);
        return ResponseEntity.ok(ResponseData.success("Form generated successfully", formBuilder));
    }
}
