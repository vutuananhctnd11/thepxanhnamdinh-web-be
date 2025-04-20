package com.graduate.be_txnd_fanzone.controller;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
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

    @PatchMapping("{groupId}")
    public ResponseEntity<ApiResponse<String>> updateGroup(@PathVariable Long groupId) {
        groupService.softDeleteGroup(groupId);
        return new ResponseEntity<>(new ApiResponse<>(null), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<GroupResponse>>> getAllGroups(@RequestParam int page, @RequestParam int limit) {
        ApiResponse<List<GroupResponse>> response = new ApiResponse<>(groupService.getAllGroups(page, limit));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
