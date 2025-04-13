package com.graduate.be_txnd_fanzone.dto.ticket;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketOfMatchResponse {

    List<TicketTypeResponse> standA;
    List<TicketTypeResponse> standB;
    List<TicketTypeResponse> standC;
    List<TicketTypeResponse> standD;
}
