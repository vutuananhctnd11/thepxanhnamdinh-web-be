package com.graduate.be_txnd_fanzone.controller;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.match.MatchInfoResponse;
import com.graduate.be_txnd_fanzone.dto.match.MatchSellTicketResponse;
import com.graduate.be_txnd_fanzone.service.MatchService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
