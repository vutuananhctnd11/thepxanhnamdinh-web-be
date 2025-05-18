package com.graduate.be_txnd_fanzone.dto.coach;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCoachRequest {

    Long coachId;
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
    String nationality;
    String address;
    String description;
    String image;
    String position;
}

