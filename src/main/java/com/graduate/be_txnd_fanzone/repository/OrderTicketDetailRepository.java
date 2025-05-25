package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.OrderTicketDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderTicketDetailRepository extends JpaRepository<OrderTicketDetail, Long> {

    @Query("""
            SELECT o.orderTicketDetailId
            FROM OrderTicketDetail o
            WHERE
                o.orderTicket.orderTicketId = :orderTicketId
    """)
    List<String> getOrderTicketDetailIdsByOrderTicketId(Long orderTicketId);
}
