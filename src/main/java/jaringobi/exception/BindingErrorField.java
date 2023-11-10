package jaringobi.exception;

import org.springframework.validation.FieldError;

public record BindingErrorField(String field, String message) {

    public static BindingErrorField of(FieldError error) {
        return new BindingErrorField(error.getField(), error.getDefaultMessage());
    }

}
