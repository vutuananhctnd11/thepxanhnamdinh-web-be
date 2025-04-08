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
    ROLE_NOT_FOUND ("Không tìm thấy quyền này!", HttpStatus.NOT_FOUND),
    USER_EXISTED ("Đã tồn tại người dùng trong hệ thống!", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND ("Không tồn tại người dùng trong hệ thống!", HttpStatus.NOT_FOUND),
    PASSWORD_INVALID ("Sai mật khẩu, vui lòng thử lại!", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED ("Đã tồn tại email trong hệ thống!", HttpStatus.BAD_REQUEST),
    UPLOAD_FAILED ("Lỗi upload file!", HttpStatus.INTERNAL_SERVER_ERROR),
    PLAYER_NOT_FOUND ("Không tìm thấy cầu thủ phù hợp!", HttpStatus.NOT_FOUND),
    COACH_NOT_FOUND ("Không tìm thấy huấn luyện viên phù hợp!", HttpStatus.NOT_FOUND),
    MATCH_NOT_FOUND ("Không tìm thấy trận đấu nào!", HttpStatus.NOT_FOUND),
    ;
    private ErrorCode (String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
    String message;
    HttpStatus httpStatus;
}
