package com.graduate.be_txnd_fanzone.dto.match;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateMatchResponse {

    Long clubId;
    Long matchId;
    LocalDateTime matchDate;
    String tournament;
    String round;
    Boolean isHome;
}
