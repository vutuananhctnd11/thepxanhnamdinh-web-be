package com.graduate.be_txnd_fanzone.dto.ticket;

import com.graduate.be_txnd_fanzone.validator.NotBlank.NotBlankConstraint;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateListTicketRequest {

    @NotBlankConstraint(name = "ID trận đấu")
    Long matchId;

    List<CreateTicketInfoRequest> standA;

    List<CreateTicketInfoRequest> standB;

    List<CreateTicketInfoRequest> standC;

    List<CreateTicketInfoRequest> standD;
}
