package com.graduate.be_txnd_fanzone.dto.match;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateResultMatchRequest {

    Long matchId;
    Byte homeScore;
    Byte awayScore;
}
