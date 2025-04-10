package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match, Long> {

    Optional<Match> findFirstByMatchDateBeforeAndStatusIgnoreCaseAndDeleteFlagIsFalseOrderByMatchDateAsc(LocalDateTime matchDate, String status);

    Optional<Match> findFirstByMatchDateAfterAndStatusIgnoreCaseAndDeleteFlagIsFalseOrderByMatchDateAsc(LocalDateTime matchDate, String status);

    List<Match> findTop2ByMatchDateBeforeAndDeleteFlagIsFalseOrderByMatchDateDesc(LocalDateTime matchDate);

    List<Match> findTop2ByMatchDateAfterAndDeleteFlagIsFalseOrderByMatchDateDesc(LocalDateTime matchDate);
}
