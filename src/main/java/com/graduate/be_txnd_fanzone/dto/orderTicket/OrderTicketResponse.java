package com.graduate.be_txnd_fanzone.dto.orderTicket;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderTicketResponse {

    String url;
    Long orderId;
}
