package fr.formbuilder.mapper;

import fr.formbuilder.dto.GenerateFormsRequest;
import fr.formbuilder.dto.FormDefinitionResponse;
import fr.formbuilder.dto.FormDefinitionResponse.*;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class FormBuilderMapper {
    public FormDefinitionResponse toResponse(GenerateFormsRequest request) {
        return FormDefinitionResponse.builder()
                .steps(request.getSteps().stream()
                        .map(FormBuilderMapper::mapStep)
                        .collect(Collectors.toList()))
                .layout(mapLayout(request.getDefaultLayout()))
                .build();
    }

    private Layout mapLayout(GenerateFormsRequest.DefaultLayout layout) {
        return Layout.builder()
                .columns(layout.getColumns())
                .gaps(layout.getGaps())
                .build();
    }

    private Step mapStep(GenerateFormsRequest.Step step) {
        return Step.builder()
                .fields(step.getFields().stream()
                        .map(FormBuilderMapper::mapField)
                        .collect(Collectors.toList()))
                .build();
    }

    private Field mapField(GenerateFormsRequest.PartialField partialField) {
        return Field.builder()
                .kind(partialField.getKind())
                .type(partialField.getType())
                .build();
    }
}

