package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByMatch_MatchIdAndStandNameAndDeleteFlagIsFalse(Long matchId, String standName);
}
