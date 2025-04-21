package com.graduate.be_txnd_fanzone.controller;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.friend.AddFriendRequest;
import com.graduate.be_txnd_fanzone.dto.friend.FriendResponse;
import com.graduate.be_txnd_fanzone.model.Friend;
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

    @PatchMapping("/accept/{friendId}")
    public ResponseEntity<ApiResponse<String>> acceptAddFriendRequest (@PathVariable Long friendId) {
        friendService.acceptAddFriendRequest(friendId);
        return new ResponseEntity<>(new ApiResponse<>(null), HttpStatus.OK);
    }

    @DeleteMapping("/reject/{friendId}")
    public ResponseEntity<ApiResponse<String>> rejectAddFriendRequest (@PathVariable Long friendId) {
        friendService.rejectAddFriendRequest(friendId);
        return new ResponseEntity<>(new ApiResponse<>(null), HttpStatus.OK);
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<ApiResponse<String>> deleteAddFriendRequest (@PathVariable Long friendId) {
        friendService.deleteAddFriendRequest(friendId);
        return new ResponseEntity<>(new ApiResponse<>(null), HttpStatus.OK);
    }

    @GetMapping("/sender")
    public ResponseEntity<ApiResponse<List<FriendResponse>>> getAddFriendRequestsOfSender (@RequestParam int page,
                                                                                        @RequestParam int limit) {
        ApiResponse<List<FriendResponse>> response = new ApiResponse<>(friendService.getAddFriendRequest(page, limit, true));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/receiver")
    public ResponseEntity<ApiResponse<List<FriendResponse>>> getAddFriendRequestsOfReceiver (@RequestParam int page,
                                                                                           @RequestParam int limit) {
        ApiResponse<List<FriendResponse>> response = new ApiResponse<>(friendService.getAddFriendRequest(page, limit, false));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FriendResponse>>> getListFriend (@RequestParam int page,
                                                                            @RequestParam int limit,
                                                                            @RequestParam Long userId) {
        ApiResponse<List<FriendResponse>> response = new ApiResponse<>(friendService.getListFriend(page, limit, userId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
