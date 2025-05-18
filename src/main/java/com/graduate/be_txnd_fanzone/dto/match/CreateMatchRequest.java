package com.graduate.be_txnd_fanzone.dto.match;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateMatchRequest {

    Long clubId;
    Boolean isHome;
    LocalDateTime matchDate;
    String tournament;
    String round;

}
