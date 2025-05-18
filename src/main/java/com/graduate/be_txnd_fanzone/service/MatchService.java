package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.PageableListResponse;
import com.graduate.be_txnd_fanzone.dto.match.CreateMatchRequest;
import com.graduate.be_txnd_fanzone.dto.match.MatchInfoResponse;
import com.graduate.be_txnd_fanzone.dto.match.MatchSellTicketResponse;
import com.graduate.be_txnd_fanzone.dto.match.UpdateMatchRequest;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    ClubRepository clubRepository;
    TicketService ticketService;

    public MatchInfoResponse getMatchInfo(Boolean isPlayed) {
        Match matchLatest;

        if (isPlayed) {
            matchLatest = matchRepository
                    .findFirstByMatchDateBeforeAndStatusAndDeleteFlagIsFalseOrderByMatchDateAsc(LocalDateTime.now(),"played")
                    .orElseThrow(() -> new CustomException(ErrorCode.MATCH_NOT_FOUND));
        } else {
            matchLatest = matchRepository
                    .findFirstByMatchDateAfterAndStatusAndDeleteFlagIsFalseOrderByMatchDateAsc(LocalDateTime.now(),"created")
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

    public MatchInfoResponse createMatch (CreateMatchRequest request) {
        Club club = clubRepository.findByClubIdAndDeleteFlagIsFalse(request.getClubId())
                .orElseThrow(() -> new CustomException(ErrorCode.CLUB_NOT_FOUND));
        Club myClub = clubRepository.findByAllowDeleteIsFalse()
                .orElseThrow(() -> new CustomException(ErrorCode.CLUB_NOT_FOUND));
        Match match = matchMapper.toMatch(request);

        if (request.getIsHome()){
            match.setHomeClub(myClub);
            match.setAwayClub(club);
        } else {
            match.setAwayClub(myClub);
            match.setHomeClub(club);
        }
        match.setStatus("created");
        matchRepository.save(match);
        return matchMapper.toMatchInfoResponse(match);
    }

    public MatchInfoResponse updateMatch (UpdateMatchRequest request) {
        Match match = matchRepository.findByMatchIdAndDeleteFlagIsFalse(request.getMatchId())
                .orElseThrow(() -> new CustomException(ErrorCode.MATCH_NOT_FOUND));
        Club club = clubRepository.findByClubIdAndDeleteFlagIsFalse(request.getClubId())
                .orElseThrow(() -> new CustomException(ErrorCode.CLUB_NOT_FOUND));
        Club myClub = clubRepository.findByAllowDeleteIsFalse()
                .orElseThrow(() -> new CustomException(ErrorCode.CLUB_NOT_FOUND));
        if (request.getIsHome()){
            match.setHomeClub(myClub);
            match.setAwayClub(club);
        } else {
            match.setAwayClub(myClub);
            match.setHomeClub(club);
        }
        matchMapper.updateMatch(match, request);
        matchRepository.save(match);
        return matchMapper.toMatchInfoResponse(match);
    }

    public void deleteMatch (Long matchId) {
        Match match = matchRepository.findByMatchIdAndDeleteFlagIsFalse(matchId)
                .orElseThrow(() -> new CustomException(ErrorCode.MATCH_NOT_FOUND));
        match.setDeleteFlag(true);
        matchRepository.save(match);
    }

    public PageableListResponse<MatchInfoResponse> getListMatch (int page, int limit){
        Pageable pageable = PageRequest.of(page, limit, Sort.by("createDate").descending());
        PageableListResponse<MatchInfoResponse> response = new PageableListResponse<>();
        Page<Match> matches = matchRepository.findAllByDeleteFlagIsFalse(pageable);
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
}
