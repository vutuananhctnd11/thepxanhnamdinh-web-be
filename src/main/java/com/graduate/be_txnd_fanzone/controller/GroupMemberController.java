package com.graduate.be_txnd_fanzone.controller;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.groupMember.AddGroupMemberRequest;
import com.graduate.be_txnd_fanzone.dto.groupMember.GroupMemberResponse;
import com.graduate.be_txnd_fanzone.service.GroupMemberService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group-members")
public class GroupMemberController {

    GroupMemberService groupMemberService;

    @PostMapping("/join-group")
    public ResponseEntity<ApiResponse<GroupMemberResponse>> joinGroupRequest(@RequestBody AddGroupMemberRequest request){
        ApiResponse<GroupMemberResponse> apiResponse = new ApiResponse<>(groupMemberService.joinGroupRequest(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
