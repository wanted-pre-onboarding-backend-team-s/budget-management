package com.saving.user.controller;

import com.saving.category.service.CategoryService;
import com.saving.common.response.ApiResponse;
import com.saving.common.response.JwtResponse;
import com.saving.user.dto.LoginRequestDto;
import com.saving.user.dto.UserCreateRequestDto;
import com.saving.user.dto.UserCreatedResponseDto;
import com.saving.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserCreatedResponseDto> createUser(
            @Valid @RequestBody UserCreateRequestDto userCreateRequestDto) {

        UserCreatedResponseDto user = userService.createUser(userCreateRequestDto);
        categoryService.createDefaultCategories(user.getId());

        return ApiResponse.created(user);
    }

    @PostMapping("/login")
    public ApiResponse<JwtResponse> login(
            @Valid @RequestBody LoginRequestDto loginRequestDto) {

        return ApiResponse.ok(userService.authenticationAndCreateJwt(loginRequestDto));
    }
}