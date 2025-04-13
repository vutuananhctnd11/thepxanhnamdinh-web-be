package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.ticket.TicketOfMatchResponse;
import com.graduate.be_txnd_fanzone.dto.ticket.TicketTypeResponse;
import com.graduate.be_txnd_fanzone.mapper.TicketMapper;
import com.graduate.be_txnd_fanzone.model.Ticket;
import com.graduate.be_txnd_fanzone.repository.TicketRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketService {

    TicketRepository ticketRepository;
    TicketMapper ticketMapper;

    public TicketOfMatchResponse getTicketOfMatch(Long matchId) {
        TicketOfMatchResponse response = new TicketOfMatchResponse();

        response.setStandA(convertToTicketTypeResponse(matchId, "A"));
        response.setStandB(convertToTicketTypeResponse(matchId, "B"));
        response.setStandC(convertToTicketTypeResponse(matchId, "C"));
        response.setStandD(convertToTicketTypeResponse(matchId, "D"));
        return response;
    }

    private List<TicketTypeResponse> convertToTicketTypeResponse(Long matchId, String standName) {
        return ticketRepository
                .findAllByMatch_MatchIdAndStandNameAndDeleteFlagIsFalse(matchId, standName)
                .stream().map(ticketMapper::toTicketTypeResponse).toList();
    }
}
