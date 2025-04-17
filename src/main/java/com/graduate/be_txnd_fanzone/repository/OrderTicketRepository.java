package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.OrderTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderTicketRepository extends JpaRepository<OrderTicket, Long> {
}
