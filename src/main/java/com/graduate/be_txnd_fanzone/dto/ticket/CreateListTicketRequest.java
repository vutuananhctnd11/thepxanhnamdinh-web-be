package com.graduate.be_txnd_fanzone.dto.ticket;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateListTicketRequest {

    Long matchId;
    List<CreateTicketInfoRequest> standA;
    List<CreateTicketInfoRequest> standB;
    List<CreateTicketInfoRequest> standC;
    List<CreateTicketInfoRequest> standD;
}
