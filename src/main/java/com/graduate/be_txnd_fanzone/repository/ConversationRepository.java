package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.dto.conversation.ConversationResponse;
import com.graduate.be_txnd_fanzone.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
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

    @Query("""
    SELECT new com.graduate.be_txnd_fanzone.dto.conversation.ConversationResponse(
        c.id,
        u.userId,
        u.firstName,
        u.lastName,
        u.avatar,
        c.type
    )
    FROM ConversationMember cm1
        JOIN cm1.conversation c
        JOIN c.members cm2
        JOIN cm2.user u
    WHERE cm1.user.userId = :userId
      AND cm2.user.userId <> :userId
      AND c.type = 0
    """)
    List<ConversationResponse> findPrivateConversationsWithUsers(@Param("userId") Long userId);
}
