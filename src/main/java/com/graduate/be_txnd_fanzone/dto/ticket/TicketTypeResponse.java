package com.graduate.be_txnd_fanzone.dto.ticket;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketTypeResponse {

    Long ticketId;
    Integer price;
    String standName;
    String position;
    String note;
}
