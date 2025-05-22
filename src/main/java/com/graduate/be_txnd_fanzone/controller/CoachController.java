package com.graduate.be_txnd_fanzone.controller;


import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.coach.CoachInfoResponse;
import com.graduate.be_txnd_fanzone.dto.coach.CoachShortInfoResponse;
import com.graduate.be_txnd_fanzone.dto.coach.CreateCoachRequest;
import com.graduate.be_txnd_fanzone.service.CoachService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<ApiResponse<CoachInfoResponse>> createCoach (@RequestBody CreateCoachRequest request) {
        ApiResponse<CoachInfoResponse> apiResponse = new ApiResponse<>(coachService.createCoach(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{coachId}")
    public ResponseEntity<ApiResponse<CoachInfoResponse>> getCoachByCoachId(@PathVariable Long coachId) {
        ApiResponse<CoachInfoResponse> apiResponse = new ApiResponse<>(coachService.getCoachInfoById(coachId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
