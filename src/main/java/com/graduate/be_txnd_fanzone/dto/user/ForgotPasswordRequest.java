package com.graduate.be_txnd_fanzone.dto.user;

import com.graduate.be_txnd_fanzone.validator.NotBlank.NotBlankConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForgotPasswordRequest {

    @NotBlankConstraint(name = "Tên đăng nhập/Email")
    String identifier;
}
