package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {

    Optional<Club> findByAllowDeleteIsFalse();
}
