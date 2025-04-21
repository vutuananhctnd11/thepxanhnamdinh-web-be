package com.graduate.be_txnd_fanzone.controller;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.groupMember.AddGroupMemberRequest;
import com.graduate.be_txnd_fanzone.dto.groupMember.GroupMemberResponse;
import com.graduate.be_txnd_fanzone.service.GroupMemberService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group-members")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GroupMemberController {

    GroupMemberService groupMemberService;

    @PostMapping("/join-group")
    public ResponseEntity<ApiResponse<GroupMemberResponse>> joinGroupRequest(@RequestBody AddGroupMemberRequest request) {
        ApiResponse<GroupMemberResponse> apiResponse = new ApiResponse<>(groupMemberService.joinGroupRequest(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PatchMapping("{groupMemberId}")
    public ResponseEntity<ApiResponse<String>> approveGroupMemberRequest(@PathVariable Long groupMemberId) {
        groupMemberService.approveMember(groupMemberId);
        return new ResponseEntity<>(new ApiResponse<>(null),HttpStatus.OK);
    }
}
