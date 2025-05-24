package com.graduate.be_txnd_fanzone.controller;

import com.cloudinary.Api;
import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.PageableListResponse;
import com.graduate.be_txnd_fanzone.dto.club.ClubResponse;
import com.graduate.be_txnd_fanzone.dto.club.CreateClubRequest;
import com.graduate.be_txnd_fanzone.dto.club.UpdateClubRequest;
import com.graduate.be_txnd_fanzone.service.ClubService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("clubs")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClubController {

    ClubService clubService;

    @PostMapping
    public ResponseEntity<ApiResponse<ClubResponse>> createClub (@RequestBody CreateClubRequest request){
        ApiResponse<ClubResponse> response = new ApiResponse<>(clubService.createClub(request));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ClubResponse>> updateClub (@RequestBody UpdateClubRequest request){
        ApiResponse<ClubResponse> response = new ApiResponse<>(clubService.updateClub(request));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{clubId}")
    public ResponseEntity<ApiResponse<String>> deleteClub (@PathVariable Long clubId){
        clubService.deleteClub(clubId);
        return new ResponseEntity<>(new ApiResponse<>(null), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<PageableListResponse<ClubResponse>>> getListClub (@RequestParam int page,
                                                                                        @RequestParam int limit){
        ApiResponse<PageableListResponse<ClubResponse>> apiResponse = new ApiResponse<>(clubService.getListClub(page, limit));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/my-club")
    public ResponseEntity<ApiResponse<ClubResponse>> getMyClub (){
        ApiResponse<ClubResponse> apiResponse = new ApiResponse<>(clubService.getMyClub());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{clubId}")
    public ResponseEntity<ApiResponse<ClubResponse>> getClubById (@PathVariable Long clubId){
        ApiResponse<ClubResponse> apiResponse = new ApiResponse<>(clubService.getClubById(clubId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("list-to-fill")
    public ResponseEntity<ApiResponse<List<ClubResponse>>> getListClubToFill () {
        ApiResponse<List<ClubResponse>> apiResponse = new ApiResponse<>(clubService.getListClubToFill());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
