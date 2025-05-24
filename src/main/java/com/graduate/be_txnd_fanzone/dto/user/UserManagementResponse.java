package com.graduate.be_txnd_fanzone.dto.user;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserManagementResponse {

    Long userId;
    String firstName;
    String lastName;
    String emailAddress;
    String phoneNumber;
    LocalDate dateOfBirth;
    String address;
    String avatar;
    Integer roleId;
}
