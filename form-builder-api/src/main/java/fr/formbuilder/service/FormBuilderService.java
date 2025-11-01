package fr.formbuilder.service;

import fr.formbuilder.dto.FormDefinitionResponse;
import fr.formbuilder.dto.GenerateFormsRequest;
import fr.formbuilder.enums.FieldKind;
import fr.formbuilder.enums.FieldType;
import fr.formbuilder.exception.InvalidFormDefinitionException;
import fr.formbuilder.mapper.FormBuilderMapper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class FormBuilderService {
    public void applyRulesForMapping(GenerateFormsRequest request) {
        Map<Predicate<GenerateFormsRequest>, String> rules = Map.of(
            r -> Objects.nonNull(request),
            "Invalid request (null).",

            r -> Objects.nonNull(r.getCount())
                    && Objects.nonNull(r.getSteps())
                    && r.getCount().equals(r.getSteps().size()),
            "Form number doesn't match number of steps.",

            r -> Objects.nonNull(r.getSteps()) && r.getSteps().stream().allMatch(
                    s -> Objects.nonNull(r.getFieldsPerStep())
                            && Objects.nonNull(s.getFields())
                            && s.getFields().size() == r.getFieldsPerStep()
            ),
            "Fields number is incorrect."
        );

        var failedRules = rules.entrySet().stream()
            .filter(entry -> !entry.getKey().test(request))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (!failedRules.isEmpty()) {
            String errorMessage = String.join("\n", failedRules.values());
            throw new InvalidFormDefinitionException(errorMessage);
        }
    }

    public FormDefinitionResponse prepareFormRequest(GenerateFormsRequest request) {
        applyRulesForMapping(request);

        request.getSteps().forEach(step ->
            step.getFields().forEach(field -> {
                if (Objects.isNull(field.getKind()) && Objects.nonNull(field.getType())) {
                    field.setKind(inferKind(field.getType()));
                }
            })
        );
        return FormBuilderMapper.toResponse(request);
    }

    private FieldKind inferKind(FieldType type) {
        return switch (type) {
            case SELECT -> FieldKind.SELECT;
            case RADIO, CHECKBOX -> FieldKind.SELECT_FILTER;
            default -> FieldKind.INPUT;
        };
    }
}
