package com.graduate.be_txnd_fanzone.controller;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.comment.CommentResponse;
import com.graduate.be_txnd_fanzone.dto.comment.CreateCommentRequest;
import com.graduate.be_txnd_fanzone.dto.comment.DeleteCommentRequest;
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
import com.graduate.be_txnd_fanzone.service.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentController {

    CommentService commentService;

    @MessageMapping("/comment.send")
    public void createComment(@Payload CreateCommentRequest request, Principal principal) {
        commentService.createComment(request, principal);
    }

    @MessageMapping("/comment.update")
    public void updateComment(@Payload UpdateCommentRequest request, Principal principal) {
        commentService.authenticate(principal);
        commentService.updateComment(request);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getListComments(@RequestParam int page, @RequestParam int limit, @RequestParam Long postId) {
        ApiResponse<List<CommentResponse>> apiResponse = new ApiResponse<>(commentService.getListComment(page, limit, postId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @MessageMapping("/comment.delete")
    public void updateComment(@Payload DeleteCommentRequest request, Principal principal) {
        commentService.authenticate(principal);
        commentService.deleteComment(request.getCommentId());
    }
}
