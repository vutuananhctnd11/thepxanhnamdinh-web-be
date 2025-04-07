package com.graduate.be_txnd_fanzone.dto.player;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayerInSquadResponse {

    Long playerId;
    String nameInShirt;
    Integer shirtNumber;
    String avatarImage;
}
