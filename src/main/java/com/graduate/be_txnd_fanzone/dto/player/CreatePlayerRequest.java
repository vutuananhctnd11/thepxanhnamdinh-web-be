package com.graduate.be_txnd_fanzone.dto.player;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreatePlayerRequest {

    Long clubId;
    String firstName;
    String lastName;
    String nameInShirt;
    Integer shirtNumber;
    LocalDate dateOfBirth;
    Float height;
    Integer weight;
    String nationality;
    String position;
    Integer goal;
    Integer assist;
    String avatarImage;
    String fullBodyImage;
    String description;

}
