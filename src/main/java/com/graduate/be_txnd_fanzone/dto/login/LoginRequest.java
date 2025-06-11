package com.graduate.be_txnd_fanzone.dto.login;

import com.graduate.be_txnd_fanzone.validator.NotBlank.NotBlankConstraint;
import com.graduate.be_txnd_fanzone.validator.Length.LengthConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {

    @NotBlankConstraint(name = "Tên đăng nhập")
    String username;

    @NotBlankConstraint(name = "Mật khẩu")
    @LengthConstraint(min = 6, max = 16, name = "Mật khẩu")
    String password;
}
