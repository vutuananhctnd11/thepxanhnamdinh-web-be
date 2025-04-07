package com.graduate.be_txnd_fanzone.dto.player;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayerInfoResponse {

    Long playerId;
    String firstName;
    String lastName;
    Integer shirtNumber;
    Integer age;
    LocalDate dateOfBirth;
    Float height;
    Integer weight;
    String nationality;
    String position;
    Integer goal;
    Integer assist;
    String fullBodyImage;
    String description;

}
