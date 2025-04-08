package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match, Long> {

    Optional<Match> findFirstByMatchDateBeforeAndStatusIgnoreCaseAndDeleteFlagIsFalseOrderByMatchDateAsc(LocalDateTime matchDate, String status);
}
