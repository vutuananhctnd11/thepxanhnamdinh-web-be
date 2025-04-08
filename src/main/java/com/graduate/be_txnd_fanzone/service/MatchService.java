package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.match.MatchInfoResponse;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.mapper.MatchMapper;
import com.graduate.be_txnd_fanzone.model.Match;
import com.graduate.be_txnd_fanzone.model.Player;
import com.graduate.be_txnd_fanzone.repository.MatchRepository;
import com.graduate.be_txnd_fanzone.repository.PlayerRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.el.stream.Optional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchService {

    MatchRepository matchRepository;
    MatchMapper matchMapper;
    PlayerRepository playerRepository;

    public MatchInfoResponse getLatestMatchResult() {
        Match matchLatest = matchRepository
                .findFirstByMatchDateBeforeAndStatusIgnoreCaseAndDeleteFlagIsFalseOrderByMatchDateAsc(LocalDateTime.now(),"Đã đá")
                .orElseThrow(() -> new CustomException(ErrorCode.MATCH_NOT_FOUND));

        MatchInfoResponse matchInfoResponse = matchMapper.toMatchInfoResponse(matchLatest);
        Long homeClubId = matchLatest.getHomeClub().getClubId();

        Player homePlayer = playerRepository.findRandomPlayerByClubId(homeClubId)
                .orElseThrow(() -> new CustomException(ErrorCode.PLAYER_NOT_FOUND));
        matchInfoResponse.setHomePlayerImage(homePlayer.getAvatarImage());
        Long awayClubId = matchLatest.getAwayClub().getClubId();

        Player awayPlayer = playerRepository.findRandomPlayerByClubId(awayClubId)
                .orElseThrow(() -> new CustomException(ErrorCode.PLAYER_NOT_FOUND));
        matchInfoResponse.setAwayPlayerImage(awayPlayer.getAvatarImage());
        return matchInfoResponse;
    }
}
