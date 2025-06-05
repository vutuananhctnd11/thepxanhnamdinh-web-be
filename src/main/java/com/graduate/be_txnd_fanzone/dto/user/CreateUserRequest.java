package com.graduate.be_txnd_fanzone.dto.user;

import com.graduate.be_txnd_fanzone.validator.Email.EmailConstraint;
import com.graduate.be_txnd_fanzone.validator.NotBlank.NotBlankConstraint;
import com.graduate.be_txnd_fanzone.validator.Size.SizeConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequest {

    @NotBlankConstraint(name = "Tên đăng nhập")
    @SizeConstraint(min = 6, max = 12, name = "Tên đăng nhập")
    String username;

    @NotBlankConstraint(name = "Mật khẩu")
    @SizeConstraint(min = 6, max = 16, name = "Mật khẩu")
    String password;

    @NotBlankConstraint(name = "Họ tên đệm")
    String firstName;

    @NotBlankConstraint(name = "Tên")
    String lastName;

    @NotBlankConstraint(name = "Địa chỉ email")
    @EmailConstraint(name = "Địa chỉ email")
    String emailAddress;
}
