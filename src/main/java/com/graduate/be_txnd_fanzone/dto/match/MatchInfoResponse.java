package com.graduate.be_txnd_fanzone.dto.match;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MatchInfoResponse {

    Long matchId;
    String homeName;
    String homeLogo;
    String homePlayerImage;
    Byte homeScore;
    String awayName;
    String awayLogo;
    String awayPlayerImage;
    Byte awayScore;
    LocalDateTime matchDate;
    String tournament;
    String round;
    String stadium;
    String status;
}
