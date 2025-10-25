package fr.formbuilder.exception;

public class InvalidFormDefinitionException extends RuntimeException {
    public InvalidFormDefinitionException(String message) {
        super(message);
    }

    public InvalidFormDefinitionException(String message, Throwable cause) {
        super(message, cause);
    }
}
