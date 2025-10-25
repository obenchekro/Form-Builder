package fr.formbuilder.dto;

import fr.formbuilder.enums.FieldKind;
import fr.formbuilder.enums.FieldType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerateFormsRequest {
    @Min(1)
    private Integer count;

    @Singular
    @NotNull
    @Valid
    private List<FormSpec> forms;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FormSpec {
        @NotNull
        private FieldKind kind;
        private String title;
        private DefaultLayout defaultLayout;

        @Min(1)
        private Integer fieldsPerStep;

        private List<Step> steps;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Step {
        @Min(1)
        private Integer count;
        private List<FieldKind> kind;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PartialField {
        @NotNull
        private FieldKind kind;
        @NonNull
        private FieldType type;
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DefaultLayout {
        @Builder.Default
        private Integer columns = 12;
        @Builder.Default
        private Integer gaps = 16;
    }
}
