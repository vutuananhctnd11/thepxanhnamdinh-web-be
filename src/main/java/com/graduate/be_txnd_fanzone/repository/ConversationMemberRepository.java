package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.dto.conversation.ConversationResponse;
import com.graduate.be_txnd_fanzone.model.ConversationMember;
import com.graduate.be_txnd_fanzone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConversationMemberRepository extends JpaRepository<ConversationMember, Long> {

    List<ConversationMember> findAllByUser_UserId(Long userId);

    List<ConversationMember> findAllByConversation_Id(Long conversationId);



}
