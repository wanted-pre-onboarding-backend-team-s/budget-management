package jaringobi.exception;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public record ErrorResponse(String code, String message, List<BindingErrorField> errorFields) {

    public static ErrorResponse of(String code, List<BindingErrorField> bindingErrorFields) {
        return new ErrorResponse(code, "필드 값 에러", bindingErrorFields);
    }

    public static ErrorResponse of(String code, String message) {
        return new ErrorResponse(code, message, null);
    }

    public static ErrorResponse of(ErrorType errorType) {
        return new ErrorResponse(errorType.getCode(), errorType.getMessage(), null);
    }

}
