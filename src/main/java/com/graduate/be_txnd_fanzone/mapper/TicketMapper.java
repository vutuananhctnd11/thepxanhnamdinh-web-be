package com.graduate.be_txnd_fanzone.mapper;

import com.graduate.be_txnd_fanzone.dto.ticket.TicketTypeResponse;
import com.graduate.be_txnd_fanzone.model.Ticket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    TicketTypeResponse toTicketTypeResponse(Ticket ticket);
}
