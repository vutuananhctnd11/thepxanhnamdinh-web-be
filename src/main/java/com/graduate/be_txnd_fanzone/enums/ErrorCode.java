package com.graduate.be_txnd_fanzone.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {

    UNAUTHORIZED ("Bạn không có quyền truy cập", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED ("Xác thực thất bại, vui lòng thử lại!", HttpStatus.UNAUTHORIZED),
    ROLE_NOT_FOUND ("Không tìm thấy quyền này!", HttpStatus.BAD_REQUEST),
    USER_EXISTED ("Đã tồn tại người dùng (tên đăng nhập) trong hệ thống!", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND ("Không tồn tại người dùng (tên đăng nhập) trong hệ thống!", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID ("Sai mật khẩu, vui lòng thử lại!", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED ("Đã tồn tại email trong hệ thống!", HttpStatus.BAD_REQUEST),
    ;
    private ErrorCode (String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
    String message;
    HttpStatus httpStatus;
}
