package fr.formbuilder.controller;

import fr.formbuilder.dto.GenerateFormsRequest;
import fr.formbuilder.dto.FormDefinitionResponse;
import fr.formbuilder.exception.InvalidFormDefinitionException;
import fr.formbuilder.mapper.FormBuilderMapper;
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
        formBuilderService.applyRulesForMapping(request);
        FormDefinitionResponse result = FormBuilderMapper.toResponse(request);
        return ResponseEntity.ok(ResponseData.success("Form generated successfully", result));
    }
}
