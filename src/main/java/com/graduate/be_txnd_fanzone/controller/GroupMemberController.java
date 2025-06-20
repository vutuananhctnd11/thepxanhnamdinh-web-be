package com.graduate.be_txnd_fanzone.controller;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.PageableListResponse;
import com.graduate.be_txnd_fanzone.dto.groupMember.AddGroupMemberRequest;
import com.graduate.be_txnd_fanzone.dto.groupMember.ChangeMemberRoleRequest;
import com.graduate.be_txnd_fanzone.dto.groupMember.CheckIsMemberResponse;
import com.graduate.be_txnd_fanzone.dto.groupMember.GroupMemberResponse;
import com.graduate.be_txnd_fanzone.service.GroupMemberService;
import jakarta.validation.Valid;
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
    public ResponseEntity<ApiResponse<GroupMemberResponse>> joinGroupRequest(@RequestBody @Valid AddGroupMemberRequest request) {
        ApiResponse<GroupMemberResponse> apiResponse = new ApiResponse<>(groupMemberService.joinGroupRequest(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PatchMapping("{groupMemberId}")
    public ResponseEntity<ApiResponse<String>> approveGroupMemberRequest(@PathVariable Long groupMemberId) {
        groupMemberService.approveMember(groupMemberId);
        return new ResponseEntity<>(new ApiResponse<>(null),HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> deleteGroupMember(@RequestParam Long groupId, @RequestParam Long userId) {
        groupMemberService.deleteGroupMember(groupId, userId);
        return new ResponseEntity<>(new ApiResponse<>(null),HttpStatus.OK);
    }

    @DeleteMapping("/join-request")
    public ResponseEntity<ApiResponse<String>> deleteJoinGroupRequest(@RequestParam Long groupId, @RequestParam Long userId) {
        groupMemberService.deleteJoinGroupRequest(groupId, userId);
        return new ResponseEntity<>(new ApiResponse<>(null),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CheckIsMemberResponse>> isMember (@RequestParam Long userId, @RequestParam Long groupId) {
        ApiResponse<CheckIsMemberResponse> apiResponse = new ApiResponse<>(groupMemberService.isMember(userId, groupId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/list-member")
    public ResponseEntity<ApiResponse<PageableListResponse<GroupMemberResponse>>> getListGroupMembers (
            @RequestParam Long groupId, @RequestParam int page, @RequestParam int limit) {
        ApiResponse<PageableListResponse<GroupMemberResponse>> apiResponse = new ApiResponse<>(
                groupMemberService.getListMember(page, limit, groupId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/manager")
    public ResponseEntity<ApiResponse<PageableListResponse<GroupMemberResponse>>> getListGroupMemberManager (
            @RequestParam Long groupId, @RequestParam int page, @RequestParam int limit) {
        ApiResponse<PageableListResponse<GroupMemberResponse>> apiResponse = new ApiResponse<>(
                groupMemberService.getListMember(page, limit, groupId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/list-join-request")
    public ResponseEntity<ApiResponse<PageableListResponse<GroupMemberResponse>>> getListRequestJoinedGroup (
            @RequestParam Long groupId, @RequestParam int page, @RequestParam int limit) {
        ApiResponse<PageableListResponse<GroupMemberResponse>> apiResponse = new ApiResponse<>(
                groupMemberService.getListRequestJoinedGroup(page, limit, groupId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PatchMapping("/change-member-role")
    public ResponseEntity<ApiResponse<String>> changeMemberRole (@RequestBody @Valid ChangeMemberRoleRequest request) {
        groupMemberService.changeMemberRole(request);
        return new ResponseEntity<>(new ApiResponse<>(null),HttpStatus.OK);
    }

}
