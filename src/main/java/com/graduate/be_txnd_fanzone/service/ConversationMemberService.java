package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.model.Conversation;
import com.graduate.be_txnd_fanzone.model.ConversationMember;
import com.graduate.be_txnd_fanzone.model.User;
import com.graduate.be_txnd_fanzone.repository.ConversationMemberRepository;
import com.graduate.be_txnd_fanzone.repository.ConversationRepository;
import com.graduate.be_txnd_fanzone.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConversationMemberService {

    ConversationMemberRepository conversationMemberRepository;
    ConversationRepository conversationRepository;
    UserRepository userRepository;

    public void create (Long userId, Long conversationId) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new CustomException(ErrorCode.CONVERSATION_NOT_FOUND));
        User user = userRepository.findByUserIdAndDeleteFlagIsFalse(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        ConversationMember conversationMember = new ConversationMember();
        conversationMember.setUser(user);
        conversationMember.setConversation(conversation);
        conversationMemberRepository.save(conversationMember);
    }
}
