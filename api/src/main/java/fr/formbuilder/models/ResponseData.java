package fr.formbuilder.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.formbuilder.enums.RequestState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@JsonInclude(NON_NULL)
public class ResponseData<T> {
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private RequestState state;
    private String message;
    private T data;
    private List<ErrorDetails> details;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ErrorDetails {
        @JsonInclude(NON_NULL)
        private String field;
        private String message;
    }

    public static <T> ResponseData<T> success(T data) {
        return ResponseData.<T>builder()
                .status(200)
                .state(RequestState.SUCCESS)
                .message("Operation successful")
                .data(data)
                .build();
    }

    public static <T> ResponseData<T> success(String message, T data) {
        return ResponseData.<T>builder()
                .status(200)
                .state(RequestState.SUCCESS)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ResponseData<T> error(int status, String message, List<ErrorDetails> errorDetails) {
        return ResponseData.<T>builder()
                .status(status)
                .state(RequestState.FAILURE)
                .message(message)
                .details(errorDetails)
                .build();
    }
}

