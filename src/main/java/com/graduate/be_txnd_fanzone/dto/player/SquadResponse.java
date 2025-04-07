package com.graduate.be_txnd_fanzone.dto.player;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SquadResponse {

    List<PlayerInSquadResponse> strikerPlayers;
    List<PlayerInSquadResponse> midfielderPlayers;
    List<PlayerInSquadResponse> defenderPlayers;
    List<PlayerInSquadResponse> goalkeeperPlayer;
    List<PlayerInSquadResponse> substitutePlayers;
}
