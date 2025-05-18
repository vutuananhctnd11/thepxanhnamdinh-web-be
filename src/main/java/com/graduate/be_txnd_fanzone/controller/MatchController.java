package com.graduate.be_txnd_fanzone.controller;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.PageableListResponse;
import com.graduate.be_txnd_fanzone.dto.match.CreateMatchRequest;
import com.graduate.be_txnd_fanzone.dto.match.MatchInfoResponse;
import com.graduate.be_txnd_fanzone.dto.match.MatchSellTicketResponse;
import com.graduate.be_txnd_fanzone.dto.match.UpdateMatchRequest;
import com.graduate.be_txnd_fanzone.dto.ticket.CreateListTicketRequest;
import com.graduate.be_txnd_fanzone.service.MatchService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matches")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchController {

    MatchService matchService;

    @GetMapping("/latest-result")
    public ResponseEntity<ApiResponse<MatchInfoResponse>> getLatestMatchResult() {
        ApiResponse<MatchInfoResponse> apiResponse = new ApiResponse<>(matchService.getMatchInfo(true));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/next-match")
    public ResponseEntity<ApiResponse<MatchInfoResponse>> getNextMatch() {
        ApiResponse<MatchInfoResponse> apiResponse = new ApiResponse<>(matchService.getMatchInfo(false));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/top3-matches-nearest")
    public ResponseEntity<ApiResponse<List<MatchSellTicketResponse>>> getTop5MatchesNearest() {
        ApiResponse<List<MatchSellTicketResponse>> apiResponse = new ApiResponse<>(matchService.getTop3MatchesNearest());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MatchInfoResponse>> createMatch (@RequestBody CreateMatchRequest request){
        ApiResponse<MatchInfoResponse> apiResponse = new ApiResponse<>(matchService.createMatch(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<MatchInfoResponse>> updateMatch (@RequestBody UpdateMatchRequest request){
        ApiResponse<MatchInfoResponse> apiResponse = new ApiResponse<>(matchService.updateMatch(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PatchMapping("/{matchId}")
    public ResponseEntity<ApiResponse<String>> deleteMatch (@PathVariable Long matchId) {
        matchService.deleteMatch(matchId);
        return new ResponseEntity<>(new ApiResponse<>(null), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<PageableListResponse<MatchInfoResponse>>> getMatchList (@RequestParam int page,
                                                                                              @RequestParam int limit) {
        ApiResponse<PageableListResponse<MatchInfoResponse>> apiResponse = new ApiResponse<>(
                matchService.getListMatch(page, limit));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PatchMapping("/open-sell-ticket")
    public ResponseEntity<ApiResponse<String>> openSellTicket (@RequestBody CreateListTicketRequest request) {
        matchService.openSellTicket(request);
        return new ResponseEntity<>(new ApiResponse<>(null), HttpStatus.OK);
    }
}
