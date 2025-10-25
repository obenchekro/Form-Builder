package fr.formbuilder.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.formbuilder.convertor.serializers.FieldLabelSerializer;
import fr.formbuilder.convertor.serializers.TitleSerializer;
import fr.formbuilder.enums.FieldType;
import fr.formbuilder.enums.FieldKind;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class FormDefinitionResponse {

    @NotBlank
    private String id;

    @NotBlank
    @JsonSerialize(using = TitleSerializer.class)
    private String title;

    @NotNull
    private Layout layout;

    @Singular
    private List<Step> steps;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(NON_NULL)
    public static class Layout {
        private Integer columns;
        private Integer gaps;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(NON_NULL)
    public static class Step {
        @NotBlank
        private String id;

        @NotBlank
        @JsonSerialize(using = FieldLabelSerializer.class)
        private String label;

        @Singular
        private List<Field> fields;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(NON_NULL)
    public static class Field {
        @NotBlank
        private String key;

        @NotNull
        private FieldType type;

        @NotNull
        private FieldKind kind;

        @NotBlank
        @JsonSerialize(using = FieldLabelSerializer.class)
        private String label;

        private Boolean required;
        private Integer colSpan;
        private Boolean multiple;

        @Singular
        private List<Option> options;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(NON_NULL)
    public static class Option {
        @NotBlank
        private String value;
        @NotBlank
        private String label;
    }
}
