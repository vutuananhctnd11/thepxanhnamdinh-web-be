package com.graduate.be_txnd_fanzone.exception;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse<Object>> handleRuntimeException(RuntimeException exception) {
        ApiResponse<Object> apiResponse = new ApiResponse<>("error","Runtime Exception");
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = CustomException.class)
    ResponseEntity<ApiResponse<Object>> handleCustomException(CustomException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse<Object> apiResponse = new ApiResponse<>("error",errorCode.getMessage());
        return new ResponseEntity<>(apiResponse, errorCode.getHttpStatus());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse<?>> handleAccessDeniedException(AccessDeniedException exception) {
        ApiResponse<?> apiResponse = new ApiResponse<>("error",ErrorCode.UNAUTHORIZED.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = ServletException.class)
    ResponseEntity<ApiResponse<?>> handleServletException(ServletException exception) {
        ApiResponse<?> apiResponse = new ApiResponse<>("error",ErrorCode.UNAUTHENTICATED.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

}
