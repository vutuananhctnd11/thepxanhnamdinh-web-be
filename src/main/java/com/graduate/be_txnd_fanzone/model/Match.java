package com.graduate.be_txnd_fanzone.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "match_day")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Match extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id", nullable = false)
    Long matchId;

    @Column(name = "home_score")
    Byte homeScore;

    @Column(name = "away_score")
    Byte awayScore;

    @Column(name = "match_date")
    LocalDateTime matchDate;

    @Column(name = "tournament")
    String tournament;

    @Column(name = "round")
    String round;

    @Column(name = "status")
    String status;

    //liên kết many to many với club


}
