package com.graduate.be_txnd_fanzone.dto.match;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MatchSellTicketResponse {

    Long matchId;
    String homeName;
    String homeLogo;
    String awayName;
    String awayLogo;
    LocalDateTime matchDate;
    String tournament;
    String round;
    String stadium;
    String status;
    Boolean sellTicket;
}
