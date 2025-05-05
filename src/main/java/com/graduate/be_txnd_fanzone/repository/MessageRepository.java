package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByConversation_IdOrderByCreateAtAsc(Long conversationId);
}
