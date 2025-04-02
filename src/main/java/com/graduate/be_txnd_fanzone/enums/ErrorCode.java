package com.graduate.be_txnd_fanzone.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {

    UNAUTHORIZED ("Bạn không có quyền truy cập", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED ("Xác thực thất bại, vui lòng thử lại!", HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND ("Không tìm thấy quyền này!", HttpStatus.NOT_FOUND),
    ;
    private ErrorCode (String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
    String message;
    HttpStatus httpStatus;
}
