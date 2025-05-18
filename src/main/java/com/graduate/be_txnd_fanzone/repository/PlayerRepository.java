package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.Player;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    List<Player> findAllByPositionContainsIgnoreCaseAndClub_ClubIdAndDeleteFlagIsFalse(String position, Long clubId);

    Optional<Player> findByPlayerIdAndDeleteFlagIsFalse(Long playerId);

    @Query(value = "SELECT * FROM player WHERE club_id = :clubId ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Player> findRandomPlayerByClubId(@Param("clubId") Long clubId);

    List<Player> findAllByClub_ClubIdAndDeleteFlagIsFalse(Long clubId);

    Page<Player> findAllByClub_ClubIdAndDeleteFlagIsFalse(Long playerId, Pageable pageable);

}
