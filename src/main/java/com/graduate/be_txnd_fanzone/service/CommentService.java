package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.comment.CommentResponse;
import com.graduate.be_txnd_fanzone.dto.comment.CreateCommentRequest;
import com.graduate.be_txnd_fanzone.dto.comment.UpdateCommentRequest;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.mapper.CommentMapper;
import com.graduate.be_txnd_fanzone.model.Comment;
import com.graduate.be_txnd_fanzone.model.Post;
import com.graduate.be_txnd_fanzone.model.User;
import com.graduate.be_txnd_fanzone.repository.CommentRepository;
import com.graduate.be_txnd_fanzone.repository.PostRepository;
import com.graduate.be_txnd_fanzone.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentService {

    UserRepository userRepository;
    PostRepository postRepository;
    CommentRepository commentRepository;
    CommentMapper commentMapper;
    SimpMessagingTemplate messagingTemplate;

    public void createComment(CreateCommentRequest request, Principal principal) {
        String username = principal.getName();
        User userComment = userRepository.findByUsernameAndDeleteFlagIsFalse(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Post postOfCmt = postRepository.findByPostIdAndDeleteFlagIsFalse(request.getPostId())
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setPost(postOfCmt);
        comment.setUser(userComment);
        commentRepository.save(comment);

        PrettyTime prettyTime = new PrettyTime(Locale.forLanguageTag("vi"));
        CommentResponse commentResponse = commentMapper.toCommentResponse(comment);
        commentResponse.setCreatedAt(prettyTime.format(comment.getCreateDate()));

        String topic = "/topic/post/" + request.getPostId();
        messagingTemplate.convertAndSend(topic, commentResponse);
    }

    public void authenticate (Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
    }

    public List<CommentResponse> getListComment (int page, int limit, Long postId){
        Pageable pageable = PageRequest.of(page-1, limit);
        List<Comment> commentList = commentRepository.findAllByPost_PostIdAndDeleteFlagIsFalseOrderByCreateDateDesc(postId, pageable).getContent();
        return commentList.stream().map(comment -> {
            CommentResponse commentResponse = commentMapper.toCommentResponse(comment);
            PrettyTime prettyTime = new PrettyTime(Locale.forLanguageTag("vi"));
            commentResponse.setCreatedAt(prettyTime.format(comment.getCreateDate()));
            if (comment.getCreateDate() != null && comment.getUpdateDate() != null) {
                commentResponse.setIsUpdate(!comment.getCreateDate().isEqual(comment.getUpdateDate()));
            }
            return commentResponse;
        }).collect(Collectors.toList());
    }

    public void updateComment (UpdateCommentRequest request){
        Comment updateComment = commentRepository.findByCommentIdAndDeleteFlagIsFalse(request.getCommentId())
                .orElseThrow(() -> new CustomException(ErrorCode.CMT_NOT_FOUND));
        updateComment.setContent(request.getContent());
        commentRepository.save(updateComment);

        PrettyTime prettyTime = new PrettyTime(Locale.forLanguageTag("vi"));
        CommentResponse commentResponse = commentMapper.toCommentResponse(updateComment);
        if (updateComment.getCreateDate() != null && updateComment.getUpdateDate() != null) {
            commentResponse.setIsUpdate(!updateComment.getCreateDate().isEqual(updateComment.getUpdateDate()));
        }
        commentResponse.setCreatedAt(prettyTime.format(updateComment.getCreateDate()));

        messagingTemplate.convertAndSend("/topic/post/" + updateComment.getPost().getPostId(), commentResponse);
    }

    public void deleteComment (Long commentId){
        Comment updateComment = commentRepository.findByCommentIdAndDeleteFlagIsFalse(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.CMT_NOT_FOUND));
        commentRepository.delete(updateComment);
        messagingTemplate.convertAndSend("/topic/comment.deleted", commentId);
        System.out.println("đã xóa ============");
    }
}
