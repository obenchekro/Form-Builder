package fr.formbuilder.controller;

import fr.formbuilder.dto.GenerateFormsRequest;
import fr.formbuilder.dto.FormDefinitionResponse;
import fr.formbuilder.exception.InvalidFormDefinitionException;
import fr.formbuilder.mapper.FormBuilderMapper;
import fr.formbuilder.models.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/form-builder")
@RequiredArgsConstructor
public class FormBuilderController {
    @PostMapping("/construct")
    public ResponseEntity<ResponseData<FormDefinitionResponse, Void>> constructForms(@Valid @RequestBody GenerateFormsRequest request) throws InvalidFormDefinitionException {
        FormDefinitionResponse result = FormBuilderMapper.toResponse(request);
        return ResponseEntity.ok(ResponseData.success("Form generated successfully", result));
    }
}
