package com.graduate.be_txnd_fanzone.dto.coach;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CoachShortInfoResponse {

    Long coachId;
    String firstName;
    String lastName;
    String position;
    String nationality;
    LocalDate dateOfBirth;
    String image;
}
