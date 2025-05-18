package com.graduate.be_txnd_fanzone.mapper;

import com.graduate.be_txnd_fanzone.dto.player.*;
import com.graduate.be_txnd_fanzone.model.Player;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    PlayerInSquadResponse toPlayerInSquadResponse (Player player);

    PlayerInfoResponse toPlayerInfoResponse (Player player);

    PlayerResponse toPlayerResponse (Player player);

    Player toPlayer (CreatePlayerRequest request);

    void updatePlayer (UpdatePlayerRequest request, @MappingTarget Player player );
}
