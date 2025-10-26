package fr.formbuilder.service;

import fr.formbuilder.dto.GenerateFormsRequest;
import fr.formbuilder.exception.InvalidFormDefinitionException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class FormBuilderService {
    public void applyRulesForMapping(GenerateFormsRequest request) {
        if (request == null) throw new InvalidFormDefinitionException("Requête invalide (null).");

        Map<Predicate<GenerateFormsRequest>, String> rules = Map.of(
                r -> r.getCount() != null
                        && r.getSteps() != null
                        && r.getCount().equals(r.getSteps().size()),
                "Le nombre de formulaires ne correspond pas au nombre d’étapes.",

                r -> r.getSteps() != null && r.getSteps().stream().allMatch(
                        s -> r.getFieldsPerStep() != null
                                && s.getFields() != null
                                && s.getFields().size() == r.getFieldsPerStep()
                ),
                "Le nombre de champs dans une ou plusieurs étapes est incorrect."
        );

        var failedRules = rules.entrySet().stream()
            .filter(entry -> !entry.getKey().test(request))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (!failedRules.isEmpty()) {
            String errorMessage = String.join(" | ", failedRules.values());
            throw new InvalidFormDefinitionException(errorMessage);
        }
    }
}
