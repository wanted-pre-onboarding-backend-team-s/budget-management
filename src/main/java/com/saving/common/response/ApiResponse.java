package com.saving.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {

    @Schema(title = "http status", description = "http 상태코드")
    private int status;

    @Schema(title = "data", description = "응답 데이터")
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), data);
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), data);
    }

    public static ApiResponse<String> noContent() {
        return new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "no content");
    }
}