package com.graduate.be_txnd_fanzone.controller;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.friend.AddFriendRequest;
import com.graduate.be_txnd_fanzone.dto.friend.FriendResponse;
import com.graduate.be_txnd_fanzone.dto.friend.ListAddFriendReceivedResponse;
import com.graduate.be_txnd_fanzone.dto.friend.ListAddFriendSentResponse;
import com.graduate.be_txnd_fanzone.service.FriendService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FriendController {

    FriendService friendService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> addFriend(@RequestBody AddFriendRequest request) {
        friendService.addFriendRequest(request);
        return new ResponseEntity<>(new ApiResponse<>(null), HttpStatus.CREATED);
    }

    @PatchMapping("/accept/{userId}")
    public ResponseEntity<ApiResponse<String>> acceptAddFriendRequest (@PathVariable Long userId) {
        friendService.acceptAddFriendRequest(userId);
        return new ResponseEntity<>(new ApiResponse<>(null), HttpStatus.OK);
    }

    @DeleteMapping("/reject/{userId}")
    public ResponseEntity<ApiResponse<String>> rejectAddFriendRequest (@PathVariable Long userId) {
        friendService.rejectAddFriendRequest(userId);
        return new ResponseEntity<>(new ApiResponse<>(null), HttpStatus.OK);
    }

    @DeleteMapping("/add-request/{userId}")
    public ResponseEntity<ApiResponse<String>> deleteAddFriendRequest (@PathVariable Long userId) {
        friendService.deleteAddFriendRequest(userId);
        return new ResponseEntity<>(new ApiResponse<>(null), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<String>> deleteFriend (@PathVariable Long userId) {
        friendService.deleteFriend(userId);
        return new ResponseEntity<>(new ApiResponse<>(null), HttpStatus.OK);
    }

    @GetMapping("/sender")
    public ResponseEntity<ApiResponse<List<ListAddFriendSentResponse>>> getAddFriendSent (@RequestParam int page,
                                                                                          @RequestParam int limit) {
        ApiResponse<List<ListAddFriendSentResponse>> response =
                new ApiResponse<>(friendService.getAddFriendSent(page, limit));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/receiver")
    public ResponseEntity<ApiResponse<List<ListAddFriendReceivedResponse>>> getAddFriendReceived (@RequestParam int page,
                                                                                                  @RequestParam int limit) {
        ApiResponse<List<ListAddFriendReceivedResponse>> response =
                new ApiResponse<>(friendService.getAddFriendReceived(page, limit));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FriendResponse>>> getListFriend (@RequestParam int page,
                                                                            @RequestParam int limit) {
        ApiResponse<List<FriendResponse>> response = new ApiResponse<>(friendService.getListFriend(page, limit));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
