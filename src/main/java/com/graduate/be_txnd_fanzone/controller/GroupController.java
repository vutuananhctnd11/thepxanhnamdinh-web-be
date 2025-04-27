package com.graduate.be_txnd_fanzone.controller;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.PageableListResponse;
import com.graduate.be_txnd_fanzone.dto.group.CreateGroupRequest;
import com.graduate.be_txnd_fanzone.dto.group.GroupResponse;
import com.graduate.be_txnd_fanzone.dto.group.UpdateGroupRequest;
import com.graduate.be_txnd_fanzone.service.GroupService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GroupController {

    GroupService groupService;

    @PostMapping
    public ResponseEntity<ApiResponse<GroupResponse>> createGroup(@RequestBody CreateGroupRequest request) {
        ApiResponse<GroupResponse> response = new ApiResponse<>(groupService.createGroup(request));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<GroupResponse>> updateGroup(@RequestBody UpdateGroupRequest request) {
        ApiResponse<GroupResponse> response = new ApiResponse<>(groupService.updateGroup(request));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{groupId}")
    public ResponseEntity<ApiResponse<String>> deleteGroup(@PathVariable Long groupId) {
        groupService.softDeleteGroup(groupId);
        return new ResponseEntity<>(new ApiResponse<>(null), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<PageableListResponse<GroupResponse>>> getAllGroups(@RequestParam int page, @RequestParam int limit) {
        ApiResponse<PageableListResponse<GroupResponse>> response = new ApiResponse<>(groupService.getAllGroups(page, limit));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/group-type")
    public ResponseEntity<ApiResponse<PageableListResponse<GroupResponse>>> getGroupsWithTypes(@RequestParam int page,
                                                                                               @RequestParam int limit,
                                                                                               @RequestParam Byte type) {
        ApiResponse<PageableListResponse<GroupResponse>> response = new ApiResponse<>(
                groupService.getListGroupsWithType(page, limit, type));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user-joined")
    public ResponseEntity<ApiResponse<PageableListResponse<GroupResponse>>> getGroupsWithUserId (@RequestParam int page,
                                                                                               @RequestParam int limit,
                                                                                               @RequestParam Long userId) {
        ApiResponse<PageableListResponse<GroupResponse>> response = new ApiResponse<>(
                groupService.getListGroupsWithUserId(page, limit, userId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<ApiResponse<GroupResponse>> getGroupById(@PathVariable Long groupId) {
        ApiResponse<GroupResponse> response = new ApiResponse<>(groupService.getGroupById(groupId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
