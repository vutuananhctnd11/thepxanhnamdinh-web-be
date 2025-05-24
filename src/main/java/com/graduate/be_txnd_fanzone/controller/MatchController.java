package com.graduate.be_txnd_fanzone.controller;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.PageableListResponse;
import com.graduate.be_txnd_fanzone.dto.match.*;
import com.graduate.be_txnd_fanzone.dto.ticket.CreateListTicketRequest;
import com.graduate.be_txnd_fanzone.service.MatchService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<ApiResponse<UpdateMatchResponse>> updateMatch (@RequestBody UpdateMatchRequest request){
        ApiResponse<UpdateMatchResponse> apiResponse = new ApiResponse<>(matchService.updateMatch(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PatchMapping("/{matchId}")
    public ResponseEntity<ApiResponse<String>> deleteMatch (@PathVariable Long matchId) {
        matchService.deleteMatch(matchId);
        return new ResponseEntity<>(new ApiResponse<>(null), HttpStatus.OK);
    }

    @GetMapping("/list-match")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<PageableListResponse<MatchInfoResponse>>> getMatchList (@RequestParam int page,
                                                                                              @RequestParam int limit) {
        ApiResponse<PageableListResponse<MatchInfoResponse>> apiResponse = new ApiResponse<>(
                matchService.getListMatch(page, limit, "created"));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/list-result")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<PageableListResponse<MatchInfoResponse>>> getResultList (@RequestParam int page,
                                                                                              @RequestParam int limit) {
        ApiResponse<PageableListResponse<MatchInfoResponse>> apiResponse = new ApiResponse<>(
                matchService.getListMatch(page, limit, "played"));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{matchId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<UpdateMatchResponse>> getMatchById (@PathVariable Long matchId) {
        ApiResponse<UpdateMatchResponse> apiResponse = new ApiResponse<>(matchService.getMatchByMatchId(matchId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PatchMapping("/open-sell-ticket")
    public ResponseEntity<ApiResponse<String>> openSellTicket (@RequestBody CreateListTicketRequest request) {
        matchService.openSellTicket(request);
        return new ResponseEntity<>(new ApiResponse<>(null), HttpStatus.OK);
    }

    @GetMapping("update-result-request")
    public ResponseEntity<ApiResponse<List<MatchInfoResponse>>> getUpdateResultRequest () {
        ApiResponse<List<MatchInfoResponse>> apiResponse = new ApiResponse<>(matchService.getListUpdateResultRequest());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PatchMapping("/update-result")
    public ResponseEntity<ApiResponse<MatchInfoResponse>> updateMatchResult (@RequestBody UpdateResultMatchRequest request){
        ApiResponse<MatchInfoResponse> apiResponse = new ApiResponse<>(matchService.updateResultMatch(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
