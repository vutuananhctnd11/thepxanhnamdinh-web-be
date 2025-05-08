package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findByConversation_IdOrderByCreateAtDesc(Long conversationId, Pageable pageable);
}
