package com.graduate.be_txnd_fanzone.dto.user;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonalPageResponse {

    Long userId;
    String avatar;
    String firstName;
    String lastName;
    String dateOfBirth;
    String phoneNumber;
    String emailAddress;
    String address;
    Long totalFriends;
    Long totalPosts;
}
