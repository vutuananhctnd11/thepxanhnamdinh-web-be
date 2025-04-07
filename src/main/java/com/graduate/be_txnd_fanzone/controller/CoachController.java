package com.graduate.be_txnd_fanzone.controller;


import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.coach.CoachInfoResponse;
import com.graduate.be_txnd_fanzone.dto.coach.CoachShortInfoResponse;
import com.graduate.be_txnd_fanzone.service.CoachService;
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
@RequestMapping("/coaches")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CoachController {

    CoachService coachService;

    @GetMapping("/head-coach")
    public ResponseEntity<ApiResponse<CoachInfoResponse>> getHeadCoachInfo() {
        ApiResponse<CoachInfoResponse> apiResponse = new ApiResponse<>(coachService.getHeadCoachInfo());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CoachShortInfoResponse>>> getAllCoach() {
        ApiResponse<List<CoachShortInfoResponse>> apiResponse = new ApiResponse<>(coachService.getAllCoach());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
