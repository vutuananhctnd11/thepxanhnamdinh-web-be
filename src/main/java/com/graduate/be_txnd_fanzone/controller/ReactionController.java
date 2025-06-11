package com.graduate.be_txnd_fanzone.controller;

import com.cloudinary.Api;
import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.reaction.CreateReactionRequest;
import com.graduate.be_txnd_fanzone.dto.reaction.ReactionResponse;
import com.graduate.be_txnd_fanzone.model.Reaction;
import com.graduate.be_txnd_fanzone.service.ReactionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reactions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReactionController {

    ReactionService reactionService;

    @PostMapping
    public ResponseEntity<ApiResponse<ReactionResponse>> createReaction(@RequestBody @Valid CreateReactionRequest request) {
        ApiResponse<ReactionResponse> apiResponse = new ApiResponse<>(reactionService.createReactionPost(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<String>> deleteReaction(@PathVariable Long postId) {
        reactionService.deleteReactionPost(postId);
        return new ResponseEntity<>(new ApiResponse<>(null), HttpStatus.OK);
    }
}
