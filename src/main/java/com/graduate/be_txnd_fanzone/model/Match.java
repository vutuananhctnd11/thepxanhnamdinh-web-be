package com.graduate.be_txnd_fanzone.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    //created, played
    @Column(name = "status")
    String status;

    @Column(name = "sell_ticket")
    Boolean sellTicket;

    @ManyToOne
    @JoinColumn(name = "home_club_id")
    Club homeClub;

    @ManyToOne
    @JoinColumn(name = "away_club_id")
    Club awayClub;


}
