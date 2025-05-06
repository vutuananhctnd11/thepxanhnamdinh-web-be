package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("""
        SELECT c FROM Conversation c
        JOIN c.members m
        WHERE c.type = 0
          AND m.user.userId IN (:userId1, :userId2)
        GROUP BY c
        HAVING COUNT(DISTINCT m.user.userId) = 2
           AND SIZE(c.members) = 2
    """)
    Optional<Conversation> findPrivateConversationBetweenUsers(Long userId1, Long userId2);
}
