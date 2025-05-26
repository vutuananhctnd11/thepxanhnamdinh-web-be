package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.PageableListResponse;
import com.graduate.be_txnd_fanzone.dto.match.*;
import com.graduate.be_txnd_fanzone.dto.ticket.CreateListTicketRequest;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.mapper.MatchMapper;
import com.graduate.be_txnd_fanzone.model.Club;
import com.graduate.be_txnd_fanzone.model.Match;
import com.graduate.be_txnd_fanzone.model.Player;
import com.graduate.be_txnd_fanzone.repository.ClubRepository;
import com.graduate.be_txnd_fanzone.repository.MatchRepository;
import com.graduate.be_txnd_fanzone.repository.PlayerRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchService {

    MatchRepository matchRepository;
    MatchMapper matchMapper;
    PlayerRepository playerRepository;
    ClubRepository clubRepository;
    TicketService ticketService;

    public MatchInfoResponse getMatchInfo(Boolean isPlayed) {
        Match matchLatest;

        if (isPlayed) {
            matchLatest = matchRepository
                    .findFirstByMatchDateBeforeAndStatusAndDeleteFlagIsFalseOrderByMatchDateAsc(LocalDateTime.now(), "played")
                    .orElseThrow(() -> new CustomException(ErrorCode.MATCH_NOT_FOUND));
        } else {
            matchLatest = matchRepository
                    .findFirstByMatchDateAfterAndStatusAndDeleteFlagIsFalseOrderByMatchDateAsc(LocalDateTime.now(), "created")
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

        List<Match> pastMatches = matchRepository.findTop1ByMatchDateBeforeAndDeleteFlagIsFalseOrderByMatchDateDesc(now).reversed();
        List<Match> futureMatches = matchRepository.findTop2ByMatchDateAfterAndDeleteFlagIsFalseOrderByMatchDateAsc(now);

        List<Match> combined = new ArrayList<>();
        combined.addAll(pastMatches);
        combined.addAll(futureMatches);

        return combined.stream().map(matchMapper::toMatchSellTicketResponse).toList();
    }

    public MatchInfoResponse createMatch(CreateMatchRequest request) {
        Club club = clubRepository.findByClubIdAndDeleteFlagIsFalse(request.getClubId())
                .orElseThrow(() -> new CustomException(ErrorCode.CLUB_NOT_FOUND));
        Club myClub = clubRepository.findByAllowDeleteIsFalse()
                .orElseThrow(() -> new CustomException(ErrorCode.CLUB_NOT_FOUND));
        Match match = matchMapper.toMatch(request);

        if (request.getIsHome()) {
            match.setHomeClub(myClub);
            match.setAwayClub(club);
        } else {
            match.setAwayClub(myClub);
            match.setHomeClub(club);
        }
        match.setStatus("created");
        match.setSellTicket(false);
        matchRepository.save(match);
        return matchMapper.toMatchInfoResponse(match);
    }

    public UpdateMatchResponse updateMatch(UpdateMatchRequest request) {
        Match match = matchRepository.findByMatchIdAndDeleteFlagIsFalse(request.getMatchId())
                .orElseThrow(() -> new CustomException(ErrorCode.MATCH_NOT_FOUND));
        Club club = clubRepository.findByClubIdAndDeleteFlagIsFalse(request.getClubId())
                .orElseThrow(() -> new CustomException(ErrorCode.CLUB_NOT_FOUND));
        Club myClub = clubRepository.findByAllowDeleteIsFalse()
                .orElseThrow(() -> new CustomException(ErrorCode.CLUB_NOT_FOUND));
        matchMapper.updateMatch(match, request);
        if (request.getIsHome()) {
            match.setHomeClub(myClub);
            match.setAwayClub(club);
        } else {
            match.setAwayClub(myClub);
            match.setHomeClub(club);
        }
        matchRepository.save(match);
        return convertToUpdateMatchResponse(match, myClub.getClubId());
    }

    public void deleteMatch(Long matchId) {
        Match match = matchRepository.findByMatchIdAndDeleteFlagIsFalse(matchId)
                .orElseThrow(() -> new CustomException(ErrorCode.MATCH_NOT_FOUND));
        match.setDeleteFlag(true);
        matchRepository.save(match);
    }

    public UpdateMatchResponse getMatchByMatchId(Long matchId) {
        Match match = matchRepository.findByMatchIdAndDeleteFlagIsFalse(matchId)
                .orElseThrow(() -> new CustomException(ErrorCode.MATCH_NOT_FOUND));
        Club myClub = clubRepository.findByAllowDeleteIsFalse()
                .orElseThrow(() -> new CustomException(ErrorCode.CLUB_NOT_FOUND));
        return convertToUpdateMatchResponse(match, myClub.getClubId());
    }

    private UpdateMatchResponse convertToUpdateMatchResponse(Match match, Long myClubId) {
        UpdateMatchResponse response = matchMapper.toUpdateMatchResponse(match);
        if (match.getAwayClub().getClubId().equals(myClubId)) {
            response.setIsHome(false);
            response.setClubId(match.getHomeClub().getClubId());
        } else {
            response.setIsHome(true);
            response.setClubId(match.getAwayClub().getClubId());
        }
        return response;
    }

    public PageableListResponse<MatchInfoResponse> getListMatch(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("matchDate").ascending());
        Page<Match> matches = matchRepository.findListUpcomingMatches(LocalDateTime.now().plusHours(3), pageable);
        return convertToPageableMatchInfoResponse(matches, page, limit);
    }

    public PageableListResponse<MatchInfoResponse> getListMatchResult(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("matchDate").ascending());
        Page<Match> matches = matchRepository.findListMatchResults(LocalDateTime.now(), pageable);
        return convertToPageableMatchInfoResponse(matches, page, limit);
    }

    private PageableListResponse<MatchInfoResponse> convertToPageableMatchInfoResponse(Page<Match> matches, int page, int limit) {
        PageableListResponse<MatchInfoResponse> response = new PageableListResponse<>();
        List<MatchInfoResponse> matchInfoResponses = matches.getContent().stream().map(matchMapper::toMatchInfoResponse).toList();
        response.setPage(page);
        response.setLimit(limit);
        response.setListResults(matchInfoResponses);
        response.setTotalPage((long) matches.getTotalPages());
        return response;
    }

    public void openSellTicket(CreateListTicketRequest request) {
        ticketService.createListTicket(request);
        Match match = matchRepository.findByMatchIdAndDeleteFlagIsFalse(request.getMatchId())
                .orElseThrow(() -> new CustomException(ErrorCode.MATCH_NOT_FOUND));
        match.setSellTicket(true);
        matchRepository.save(match);
    }

    public List<MatchInfoResponse> getListUpdateResultRequest () {
        List<Match> matches = matchRepository.findMatchesBefore(LocalDateTime.now().plusHours(3));
        return matches.stream().map(matchMapper::toMatchInfoResponse).collect(Collectors.toList());
    }

    public MatchInfoResponse updateResultMatch(UpdateResultMatchRequest request) {
        Match match = matchRepository.findByMatchIdAndDeleteFlagIsFalse(request.getMatchId())
                .orElseThrow(() -> new CustomException(ErrorCode.MATCH_NOT_FOUND));
        match.setStatus("played");
        match.setHomeScore(request.getHomeScore());
        match.setAwayScore(request.getAwayScore());
        matchRepository.save(match);
        return matchMapper.toMatchInfoResponse(match);
    }

    public PageableListResponse<MatchInfoResponse> getListHomeMatch() {
        Pageable pageable = PageRequest.of(0, 3, Sort.by("matchDate").ascending());
        Club myClub = clubRepository.findByAllowDeleteIsFalse().orElseThrow(() -> new CustomException(ErrorCode.CLUB_NOT_FOUND));
        log.info("myClub: " + myClub.getClubId());
        Page<Match> matches = matchRepository.findListHomeMatch(myClub.getClubId(), pageable);
        return convertToPageableMatchInfoResponse(matches, 0, 3);
    }
}
