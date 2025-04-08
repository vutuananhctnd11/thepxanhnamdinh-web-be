package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.player.PlayerInSquadResponse;
import com.graduate.be_txnd_fanzone.dto.player.PlayerInfoResponse;
import com.graduate.be_txnd_fanzone.dto.player.SquadResponse;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.mapper.PlayerMapper;
import com.graduate.be_txnd_fanzone.model.Player;
import com.graduate.be_txnd_fanzone.repository.PlayerRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlayerService {

    PlayerRepository playerRepository;

    PlayerMapper playerMapper;

    public SquadResponse getSquad() {
        SquadResponse squad = new SquadResponse();
        List<PlayerInSquadResponse> randomPlayers;

        randomPlayers = randomPlayersByPosition("tiền đạo", 3);
        Set<Long> squadPlayerId = new HashSet<>(listSquadPlayerId(randomPlayers));
        squad.setStrikerPlayers(randomPlayers);

        randomPlayers = randomPlayersByPosition("tiền vệ", 3);
        squadPlayerId.addAll(listSquadPlayerId(randomPlayers));
        squad.setMidfielderPlayers(randomPlayers);

        randomPlayers = randomPlayersByPosition("hậu vệ", 4);
        squadPlayerId.addAll(listSquadPlayerId(randomPlayers));
        squad.setDefenderPlayers(randomPlayers);

        randomPlayers = randomPlayersByPosition("thủ môn", 1);
        squadPlayerId.addAll(listSquadPlayerId(randomPlayers));
        squad.setGoalkeeperPlayer(randomPlayers);

        List<PlayerInSquadResponse> substitutePlayers = new ArrayList<>(playerRepository.findAll()
                .stream().map(playerMapper::toPlayerInSquadResponse).toList());
        substitutePlayers.removeIf(player -> squadPlayerId.contains(player.getPlayerId()));
        squad.setSubstitutePlayers(substitutePlayers);

        return squad;
    }

    public PlayerInfoResponse getPlayerInfo(Long playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow(() ->
                new CustomException(ErrorCode.PLAYER_NOT_FOUND));
        return playerMapper.toPlayerInfoResponse(player);
    }

    private List<PlayerInSquadResponse> randomPlayersByPosition(String position,int limit) {
        List<Player> players = playerRepository.findAllByPositionContainsIgnoreCaseAndDeleteFlagIsFalse(position)
                .orElseThrow(() -> new CustomException(ErrorCode.PLAYER_NOT_FOUND));
        Collections.shuffle(players);
        List<PlayerInSquadResponse> playerInSquadResponses = players.stream()
                .map(playerMapper::toPlayerInSquadResponse)
                .toList();
        return playerInSquadResponses.stream().limit(limit).toList();
    }

    private List<Long> listSquadPlayerId(List<PlayerInSquadResponse> playerInSquadResponses) {
        return playerInSquadResponses.stream()
                .map(PlayerInSquadResponse::getPlayerId)
                .toList();
    }

}
