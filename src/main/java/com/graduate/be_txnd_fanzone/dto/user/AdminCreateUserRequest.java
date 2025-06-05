package com.graduate.be_txnd_fanzone.dto.user;

import com.graduate.be_txnd_fanzone.validator.Email.EmailConstraint;
import com.graduate.be_txnd_fanzone.validator.NotBlank.NotBlankConstraint;
import com.graduate.be_txnd_fanzone.validator.Size.SizeConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminCreateUserRequest {

    @NotBlankConstraint(name = "Tên đăng nhập")
    @SizeConstraint(min = 6, max = 12, name = "Tên đăng nhập")
    String username;

    @NotBlankConstraint(name="Họ tên đệm")
    String firstName;

    @NotBlankConstraint(name = "Tên")
    String lastName;

    @NotBlankConstraint(name = "Quyền tài khoản")
    Integer roleId;

    @NotBlankConstraint(name = "Mật khẩu")
    @SizeConstraint(min = 6, max = 16, name = "Mật khẩu")
    String password;

    @NotBlankConstraint(name = "Địa chỉ email")
    @EmailConstraint(name = "Địa chỉ email")
    String emailAddress;

    @NotBlankConstraint(name = "Ảnh đại diện")
    String avatar;

}
