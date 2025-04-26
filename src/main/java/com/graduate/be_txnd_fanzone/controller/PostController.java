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

import java.util.List;

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

    @PatchMapping("/{postId}/reject")
    public ResponseEntity<ApiResponse<String>> rejectGroupPost (@PathVariable Long postId) {
        postService.rejectGroupPost(postId);
        return new ResponseEntity<>(new ApiResponse<>(null),HttpStatus.OK);
    }

    @GetMapping("/news-feed")
    public ResponseEntity<ApiResponse<List<NewsFeedResponse>>> getNewsFeed(@RequestParam int page, @RequestParam int limit) {
        ApiResponse<List<NewsFeedResponse>> apiResponse = new ApiResponse<>(postService.getNewsFeed(page, limit));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<NewsFeedResponse>> getPostByPostId(@RequestParam Long postId) {
        ApiResponse<NewsFeedResponse> apiResponse = new ApiResponse<>(postService.getPostByPostId(postId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }







}
