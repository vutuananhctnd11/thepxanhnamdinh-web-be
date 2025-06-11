package com.graduate.be_txnd_fanzone.controller;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.PageableListResponse;
import com.graduate.be_txnd_fanzone.dto.player.*;
import com.graduate.be_txnd_fanzone.repository.PlayerRepository;
import com.graduate.be_txnd_fanzone.service.PlayerService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlayerController {

    PlayerService playerService;

    @GetMapping("/squad")
    public ResponseEntity<ApiResponse<SquadResponse>> getSquad(){
        ApiResponse<SquadResponse> apiResponse = new ApiResponse<>(playerService.getSquad());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<ApiResponse<PlayerInfoResponse>> getPlayerInfo(@PathVariable Long playerId){
        ApiResponse<PlayerInfoResponse> apiResponse = new ApiResponse<>(playerService.getPlayerInfo(playerId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<PageableListResponse<PlayerResponse>>> getPlayerInfoList(@RequestParam int page,
                                                                                               @RequestParam int limit){
        ApiResponse<PageableListResponse<PlayerResponse>> apiResponse = new ApiResponse<>(playerService.getListPlayer(page, limit));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<PlayerResponse>> createPlayer (@RequestBody @Valid CreatePlayerRequest request) {
        ApiResponse<PlayerResponse> apiResponse = new ApiResponse<>(playerService.createPlayer(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<PlayerInfoResponse>> updatePlayer (@RequestBody @Valid UpdatePlayerRequest request) {
        ApiResponse<PlayerInfoResponse> apiResponse =  new ApiResponse<>(playerService.updatePlayer(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PatchMapping("/{playerId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<String>> deletePlayer (@PathVariable Long playerId){
        playerService.deletePlayer(playerId);
        return new ResponseEntity<>(new ApiResponse<>(null), HttpStatus.OK);
    }
}
