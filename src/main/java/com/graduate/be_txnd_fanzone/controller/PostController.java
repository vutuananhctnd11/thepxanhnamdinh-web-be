package com.graduate.be_txnd_fanzone.controller;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.post.*;
import com.graduate.be_txnd_fanzone.service.PostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {

    PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreatePostResponse>> createPost(@RequestBody CreatePostRequest request) {
        ApiResponse<CreatePostResponse> apiResponse = new ApiResponse<>(postService.createPost(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<UpdatePostResponse>> updatePost (@RequestBody UpdatePostRequest request) {
        ApiResponse<UpdatePostResponse> apiResponse = new ApiResponse<>(postService.updatePost(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PatchMapping("/status")
    public ResponseEntity<ApiResponse<String>> changePostStatus (@RequestBody UpdatePostStatusRequest request) {
        postService.changeStatus(request);
        return new ResponseEntity<>(new ApiResponse<>(null),HttpStatus.OK);
    }

    @PatchMapping("/{postId}/delete")
    public ResponseEntity<ApiResponse<String>> deletePost (@PathVariable Long postId) {
        postService.softDeleteOrRestorePost(postId, true);
        return new ResponseEntity<>(new ApiResponse<>(null),HttpStatus.OK);
    }

    @PatchMapping("/{postId}/restore")
    public ResponseEntity<ApiResponse<String>> restorePost (@PathVariable Long postId) {
        postService.softDeleteOrRestorePost(postId, false);
        return new ResponseEntity<>(new ApiResponse<>(null),HttpStatus.OK);
    }

    @PatchMapping("/{postId}/approve")
    public ResponseEntity<ApiResponse<String>> approveGroupPost (@PathVariable Long postId) {
        postService.approveGroupPost(postId);
        return new ResponseEntity<>(new ApiResponse<>(null),HttpStatus.OK);
    }





}
