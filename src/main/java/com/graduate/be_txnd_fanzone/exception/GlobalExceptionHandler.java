package com.graduate.be_txnd_fanzone.exception;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

}
