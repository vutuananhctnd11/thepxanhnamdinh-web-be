package com.graduate.be_txnd_fanzone.dto.user;

import com.graduate.be_txnd_fanzone.validator.DateOfBirth.DobConstraint;
import com.graduate.be_txnd_fanzone.validator.Email.EmailConstraint;
import com.graduate.be_txnd_fanzone.validator.NotBlank.NotBlankConstraint;
import com.graduate.be_txnd_fanzone.validator.Phone.PhoneConstraint;
import com.graduate.be_txnd_fanzone.validator.Length.LengthConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequest {

    @LengthConstraint(min = 6, max = 16, name = "Mật khẩu")
    String password;

    @NotBlankConstraint(name = "Họ tên đệm")
    String firstName;

    @NotBlankConstraint(name = "Tên")
    String lastName;

    @NotBlankConstraint(name = "Ngày sinh")
    @DobConstraint(min = 16)
    LocalDate dateOfBirth;

    @NotBlankConstraint(name = "Địa chỉ email")
    @EmailConstraint(name = "Địa chỉ email")
    String emailAddress;

    @NotBlankConstraint(name = "Địa chỉ")
    String address;

    @PhoneConstraint(name = "Số điện thoại")
    String phoneNumber;

    String avatar;
}
