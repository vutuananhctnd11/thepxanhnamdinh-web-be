package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.player.PlayerInSquadResponse;
import com.graduate.be_txnd_fanzone.dto.player.PlayerInfoResponse;
import com.graduate.be_txnd_fanzone.dto.player.SquadResponse;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.mapper.PlayerMapper;
import com.graduate.be_txnd_fanzone.model.Club;
import com.graduate.be_txnd_fanzone.model.Player;
import com.graduate.be_txnd_fanzone.repository.ClubRepository;
import com.graduate.be_txnd_fanzone.repository.PlayerRepository;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlayerService {

    PlayerRepository playerRepository;
    PlayerMapper playerMapper;
    ClubRepository clubRepository;

    @NonFinal
    Club myClub;

    @NonFinal
    Long myClubId;

    @PostConstruct
    public void init() {
        myClub = clubRepository.findByAllowDeleteIsFalse()
                .orElseThrow(() -> new CustomException(ErrorCode.CLUB_NOT_FOUND));
        myClubId = myClub.getClubId();
    }

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

        List<PlayerInSquadResponse> substitutePlayers = new ArrayList<>(playerRepository.findAllByClub_ClubIdAndDeleteFlagIsFalse(myClubId)
                .stream().map(playerMapper::toPlayerInSquadResponse).toList());
        substitutePlayers.removeIf(player -> squadPlayerId.contains(player.getPlayerId()));
        squad.setSubstitutePlayers(substitutePlayers);

        return squad;
    }

    public PlayerInfoResponse getPlayerInfo(Long playerId) {
        Player player = playerRepository.findByPlayerIdAndDeleteFlagIsFalse(playerId).orElseThrow(() ->
                new CustomException(ErrorCode.PLAYER_NOT_FOUND));
        return playerMapper.toPlayerInfoResponse(player);
    }

    private List<PlayerInSquadResponse> randomPlayersByPosition(String position,int limit) {
        List<Player> players = playerRepository
                .findAllByPositionContainsIgnoreCaseAndClub_ClubIdAndDeleteFlagIsFalse(position, myClubId);
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
