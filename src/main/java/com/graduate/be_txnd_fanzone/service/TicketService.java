package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.ticket.CreateListTicketRequest;
import com.graduate.be_txnd_fanzone.dto.ticket.CreateTicketInfoRequest;
import com.graduate.be_txnd_fanzone.dto.ticket.TicketOfMatchResponse;
import com.graduate.be_txnd_fanzone.dto.ticket.TicketTypeResponse;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.mapper.TicketMapper;
import com.graduate.be_txnd_fanzone.model.Match;
import com.graduate.be_txnd_fanzone.model.Ticket;
import com.graduate.be_txnd_fanzone.repository.MatchRepository;
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
    MatchRepository matchRepository;

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
                .stream().map(ticket -> {
                    TicketTypeResponse response = ticketMapper.toTicketTypeResponse(ticket);
                    if (ticket.getQuantity() == 0) {
                        response.setNote("Đã bán hết vé");
                    }
                    return response;
                }).toList();
    }

    public void createListTicket(CreateListTicketRequest request) {
        Match match = matchRepository.findById(request.getMatchId())
                .orElseThrow(() -> new CustomException(ErrorCode.MATCH_NOT_FOUND));

        saveTickets(request.getStandA(), "A", match);
        saveTickets(request.getStandB(), "B", match);
        saveTickets(request.getStandC(), "C", match);
        saveTickets(request.getStandD(), "D", match);

        match.setSellTicket(true);
    }

    private void saveTickets(List<CreateTicketInfoRequest> ticketRequests, String standName, Match match) {
        ticketRequests.forEach(req -> {
            Ticket ticket = ticketMapper.toTicket(req);
            ticket.setStandName(standName);
            ticket.setMatch(match);
            ticketRepository.save(ticket);
        });
    }


}
