package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {

    Optional<Coach> findByPositionIgnoreCase(String position);

    Optional<Coach> findByCoachIdAndDeleteFlagIsFalse(Long coachId);

    List<Coach> findAllByDeleteFlagIsFalse();

}
