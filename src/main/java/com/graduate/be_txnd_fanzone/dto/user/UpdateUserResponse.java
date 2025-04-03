package com.graduate.be_txnd_fanzone.dto.user;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserResponse {

    Long userId;
    String username;
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
    String emailAddress;
    String address;
    String phoneNumber;
    String role;
}
