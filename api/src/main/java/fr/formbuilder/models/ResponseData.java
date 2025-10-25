package fr.formbuilder.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.formbuilder.enums.RequestState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@JsonInclude(NON_NULL)
public class ResponseData<TData, TErrorDetails> {
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private RequestState state;
    private String message;
    private TData data;
    private TErrorDetails details;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ErrorDetails {
        private String field;
        private String message;
    }

    public static <TData> ResponseData<TData, Void> success(TData data) {
        return ResponseData.<TData, Void>builder()
                .status(200)
                .state(RequestState.SUCCESS)
                .message("Operation successful")
                .data(data)
                .build();
    }

    public static <TData> ResponseData<TData, Void> success(String message, TData data) {
        return ResponseData.<TData, Void>builder()
                .status(200)
                .state(RequestState.SUCCESS)
                .message(message)
                .data(data)
                .build();
    }

    public static <TErrorDetails> ResponseData<Void, TErrorDetails> error(int status, String message, TErrorDetails errorDetails) {
        return ResponseData.<Void, TErrorDetails>builder()
                .status(status)
                .state(RequestState.FAILURE)
                .message(message)
                .details(errorDetails)
                .build();
    }
}

