package com.graduate.be_txnd_fanzone.controller;


import com.cloudinary.Api;
import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.coach.CoachInfoResponse;
import com.graduate.be_txnd_fanzone.dto.coach.CoachShortInfoResponse;
import com.graduate.be_txnd_fanzone.dto.coach.CreateCoachRequest;
import com.graduate.be_txnd_fanzone.dto.coach.UpdateCoachRequest;
import com.graduate.be_txnd_fanzone.service.CoachService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<CoachInfoResponse>> createCoach (@RequestBody @Valid CreateCoachRequest request) {
        ApiResponse<CoachInfoResponse> apiResponse = new ApiResponse<>(coachService.createCoach(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{coachId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<CoachInfoResponse>> getCoachByCoachId(@PathVariable Long coachId) {
        ApiResponse<CoachInfoResponse> apiResponse = new ApiResponse<>(coachService.getCoachInfoById(coachId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<CoachInfoResponse>> updateCoach (@RequestBody @Valid UpdateCoachRequest request) {
        ApiResponse<CoachInfoResponse> apiResponse = new ApiResponse<>(coachService.updateCoach(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PatchMapping("/{coachId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteCoach (@PathVariable Long coachId) {
        coachService.deleteCoach(coachId);
        return new ResponseEntity<>(new ApiResponse<>(null), HttpStatus.OK);
    }
}
