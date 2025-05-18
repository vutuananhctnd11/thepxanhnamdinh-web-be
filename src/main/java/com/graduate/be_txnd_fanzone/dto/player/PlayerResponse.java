package com.graduate.be_txnd_fanzone.dto.player;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayerResponse {

    Long playerId;
    String firstName;
    String lastName;
    Integer age;
    String nationality;
    Integer shirtNumber;
    String position;
    String avatarImage;
}
