package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.dto.orderTicket.OrderTicketHistoryResponse;
import com.graduate.be_txnd_fanzone.model.OrderTicket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderTicketRepository extends JpaRepository<OrderTicket, Long> {

    Optional<OrderTicket> findByOrderTicketIdAndDeleteFlagIsFalse(Long orderTicketId);

    @Query("""
        SELECT new com.graduate.be_txnd_fanzone.dto.orderTicket.OrderTicketHistoryResponse(
            o.orderTicketId,
            o.createDate,
            o.status,
            COUNT(od.orderTicketDetailId),
            SUM(t.price)
        )
        FROM OrderTicket o
            JOIN OrderTicketDetail od ON o.orderTicketId = od.orderTicket.orderTicketId
            JOIN Ticket t ON od.ticket.ticketId = t.ticketId
        WHERE
            o.user.userId = :userId
        GROUP BY o.orderTicketId, o.createDate, o.status
""")
    Page<OrderTicketHistoryResponse> getListOrderTicketDetailOfUserLogin(Long userId, Pageable pageable);
}
