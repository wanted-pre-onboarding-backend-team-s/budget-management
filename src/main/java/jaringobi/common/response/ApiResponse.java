package jaringobi.common.response;

import org.springframework.http.HttpStatus;

public record ApiResponse<T>(int code, T data) {

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), data);
    }

    public static <T> ApiResponse<T> noContent() {
        return new ApiResponse<>(HttpStatus.NO_CONTENT.value(), null);
    }
}
