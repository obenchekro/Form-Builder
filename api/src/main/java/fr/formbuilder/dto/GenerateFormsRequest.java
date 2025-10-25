package fr.formbuilder.dto;

import fr.formbuilder.enums.FieldKind;
import fr.formbuilder.enums.FieldType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerateFormsRequest {
    @Min(1)
    private Integer count;

    private String title;

    @Valid
    @Builder.Default
    private DefaultLayout defaultLayout = new DefaultLayout();

    @Min(1)
    private Integer fieldsPerStep;
    @NotEmpty
    @Valid
    @Builder.Default
    private List<Step> steps = new ArrayList<>();

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Step {
        @Min(1)
        private Integer count;
        @NotEmpty
        @Valid
        private List<PartialField> fields;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PartialField {
        @NotNull
        private FieldKind kind;
        @NotNull
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
