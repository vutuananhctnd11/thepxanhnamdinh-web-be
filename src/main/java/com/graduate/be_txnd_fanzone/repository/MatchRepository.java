package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match, Long> {

    Optional<Match> findFirstByMatchDateBeforeAndStatusAndDeleteFlagIsFalseOrderByMatchDateAsc(LocalDateTime matchDate, String status);

    Optional<Match> findFirstByMatchDateAfterAndStatusAndDeleteFlagIsFalseOrderByMatchDateAsc(LocalDateTime matchDate, String status);

    List<Match> findTop2ByMatchDateBeforeAndDeleteFlagIsFalseOrderByMatchDateDesc(LocalDateTime matchDate);

    List<Match> findTop2ByMatchDateAfterAndDeleteFlagIsFalseOrderByMatchDateDesc(LocalDateTime matchDate);

    Optional<Match> findByMatchIdAndDeleteFlagIsFalse(Long matchId);

    Page<Match> findByStatusAndDeleteFlagIsFalse(String status, Pageable pageable);

    @Query("SELECT m FROM Match m " +
            "WHERE m.matchDate < :thresholdTime " +
            "AND m.status = 'created' " +
            "AND m.deleteFlag = false")
    List<Match> findMatchesBefore(@Param("thresholdTime") LocalDateTime thresholdTime);
}
