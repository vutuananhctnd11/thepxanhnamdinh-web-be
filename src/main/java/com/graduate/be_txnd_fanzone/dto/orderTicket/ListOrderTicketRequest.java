package com.graduate.be_txnd_fanzone.dto.orderTicket;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListOrderTicketRequest {

    List<OrderTicketInfoRequest> listOrderTickets;
}
