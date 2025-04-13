package com.graduate.be_txnd_fanzone.mapper;

import com.graduate.be_txnd_fanzone.dto.player.PlayerInSquadResponse;
import com.graduate.be_txnd_fanzone.dto.player.PlayerInfoResponse;
import com.graduate.be_txnd_fanzone.model.Player;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    PlayerInSquadResponse toPlayerInSquadResponse (Player player);

    PlayerInfoResponse toPlayerInfoResponse (Player player);
}
