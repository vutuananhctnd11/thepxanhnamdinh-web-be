package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<List<Player>> findAllByPositionContainsIgnoreCase(String position);
}
