package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.ConversationMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationMemberRepository extends JpaRepository<ConversationMember, Long> {

    List<ConversationMember> findAllByUser_UserId(Long userId);
}
