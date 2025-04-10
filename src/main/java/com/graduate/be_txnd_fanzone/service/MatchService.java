package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.match.MatchInfoResponse;
import com.graduate.be_txnd_fanzone.dto.match.MatchSellTicketResponse;
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

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchService {

    MatchRepository matchRepository;
    MatchMapper matchMapper;
    PlayerRepository playerRepository;

    public MatchInfoResponse getMatchInfo(Boolean isPlayed) {
        Match matchLatest;

        if (isPlayed) {
            matchLatest = matchRepository
                    .findFirstByMatchDateBeforeAndStatusIgnoreCaseAndDeleteFlagIsFalseOrderByMatchDateAsc(LocalDateTime.now(),"Đã đá")
                    .orElseThrow(() -> new CustomException(ErrorCode.MATCH_NOT_FOUND));
        } else {
            matchLatest = matchRepository
                    .findFirstByMatchDateAfterAndStatusIgnoreCaseAndDeleteFlagIsFalseOrderByMatchDateAsc(LocalDateTime.now(),"chưa đá")
                    .orElseThrow(() -> new CustomException(ErrorCode.MATCH_NOT_FOUND));
        }
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

    public List<MatchSellTicketResponse> getTop3MatchesNearest() {
        LocalDateTime now = LocalDateTime.now();

        List<Match> pastMatches = matchRepository.findTop2ByMatchDateBeforeAndDeleteFlagIsFalseOrderByMatchDateDesc(now);
        List<Match> futureMatches = matchRepository.findTop2ByMatchDateAfterAndDeleteFlagIsFalseOrderByMatchDateDesc(now);

        List<Match> combined = new ArrayList<>();
        combined.addAll(pastMatches);
        combined.addAll(futureMatches);
        combined.sort(Comparator.comparingLong(m -> Math.abs(Duration.between(m.getMatchDate(), now).toMillis())));

        List<Match> top3Matches = combined.stream().limit(3).toList();
        return top3Matches.stream().map(matchMapper::toMatchSellTicketResponse).toList();
    }
}
