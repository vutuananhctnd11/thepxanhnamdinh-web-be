package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.reaction.CreateReactionRequest;
import com.graduate.be_txnd_fanzone.dto.reaction.ReactionResponse;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.mapper.ReactionMapper;
import com.graduate.be_txnd_fanzone.model.Post;
import com.graduate.be_txnd_fanzone.model.Reaction;
import com.graduate.be_txnd_fanzone.model.User;
import com.graduate.be_txnd_fanzone.repository.PostRepository;
import com.graduate.be_txnd_fanzone.repository.ReactionRepository;
import com.graduate.be_txnd_fanzone.repository.UserRepository;
import com.graduate.be_txnd_fanzone.util.SecurityUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReactionService {

    ReactionRepository reactionRepository;
    UserRepository userRepository;
    PostRepository postRepository;
    ReactionMapper reactionMapper;
    SecurityUtil securityUtil;

    public ReactionResponse createReactionPost (CreateReactionRequest request) {
        User user = userRepository.findByUserIdAndDeleteFlagIsFalse(request.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Post post = postRepository.findByPostIdAndDeleteFlagIsFalse(request.getPostId())
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        Reaction reaction = new Reaction();
        reaction.setUser(user);
        reaction.setPost(post);
        reactionRepository.save(reaction);
        return reactionMapper.toReactionResponse(reaction);
    }

    public void deleteReactionPost (Long postId) {
        Long userLoginId = securityUtil.getCurrentUserId();
        Reaction reaction = reactionRepository.findByPost_PostIdAndUser_UserId(postId, userLoginId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        reactionRepository.delete(reaction);
    }
}
