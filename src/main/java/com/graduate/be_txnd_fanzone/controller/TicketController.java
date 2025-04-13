package com.graduate.be_txnd_fanzone.controller;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.ticket.TicketOfMatchResponse;
import com.graduate.be_txnd_fanzone.service.TicketService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketController {

    TicketService ticketService;

    @GetMapping("/ticket-of-match")
    public ResponseEntity<ApiResponse<TicketOfMatchResponse>> getTicketOfMatch(@RequestParam Long matchId) {
        ApiResponse<TicketOfMatchResponse> apiResponse = new ApiResponse<>(ticketService.getTicketOfMatch(matchId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
