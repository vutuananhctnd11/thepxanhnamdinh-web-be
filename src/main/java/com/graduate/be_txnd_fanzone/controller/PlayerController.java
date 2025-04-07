package com.graduate.be_txnd_fanzone.controller;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.player.PlayerInfoResponse;
import com.graduate.be_txnd_fanzone.dto.player.SquadResponse;
import com.graduate.be_txnd_fanzone.repository.PlayerRepository;
import com.graduate.be_txnd_fanzone.service.PlayerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

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
}
