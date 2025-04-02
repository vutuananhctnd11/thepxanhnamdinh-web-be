package com.graduate.be_txnd_fanzone.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequest {

    @NotBlank(message = "Không được để trống tên đăng nhập")
    @Size(min = 8)
    String username;
    String password;
    String firstName;
    String lastName;
    String emailAddress;
}
