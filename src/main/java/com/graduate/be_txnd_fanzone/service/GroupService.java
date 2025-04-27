package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.PageableListResponse;
import com.graduate.be_txnd_fanzone.dto.group.CreateGroupRequest;
import com.graduate.be_txnd_fanzone.dto.group.GroupResponse;
import com.graduate.be_txnd_fanzone.dto.group.UpdateGroupRequest;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.mapper.GroupMapper;
import com.graduate.be_txnd_fanzone.model.Group;
import com.graduate.be_txnd_fanzone.model.GroupMember;
import com.graduate.be_txnd_fanzone.model.User;
import com.graduate.be_txnd_fanzone.repository.GroupMemberRepository;
import com.graduate.be_txnd_fanzone.repository.GroupRepository;
import com.graduate.be_txnd_fanzone.repository.UserRepository;
import com.graduate.be_txnd_fanzone.util.SecurityUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GroupService {

    GroupRepository groupRepository;
    GroupMapper groupMapper;
    GroupMemberService groupMemberService;
    SecurityUtil securityUtil;
    GroupMemberRepository groupMemberRepository;

    @Transactional
    public GroupResponse createGroup(CreateGroupRequest request) {
        Group group = groupMapper.toGroup(request);
        groupRepository.save(group);

        //create admin in group
        groupMemberService.addAdminGroup(group.getCreateBy(), group.getGroupId());

        return groupMapper.toGroupResponse(group);
    }

    public GroupResponse updateGroup (UpdateGroupRequest request) {
        Long userLoginId = securityUtil.getCurrentUserId();
        boolean isAdminGroup = groupMemberRepository
                .existsByUser_UserIdAndGroup_GroupIdAndMemberRoleAndDeleteFlagIsFalse(userLoginId, request.getGroupId(), (byte) 2);
        if ( !isAdminGroup) {
            throw new CustomException(ErrorCode.NO_PERMISSION);
        }
        Group groupUpdate = groupRepository.findByGroupIdAndDeleteFlagIsFalse(request.getGroupId())
                .orElseThrow(() -> new CustomException(ErrorCode.GROUP_NOT_FOUND));
        groupUpdate = groupMapper.updateGroup(request,groupUpdate);
        groupRepository.save(groupUpdate);
        return groupMapper.toGroupResponse(groupUpdate);
    }

    public void softDeleteGroup(Long groupId) {
        Group group = groupRepository.findByGroupIdAndDeleteFlagIsFalse(groupId)
                .orElseThrow(() -> new CustomException(ErrorCode.GROUP_NOT_FOUND));
        group.setDeleteFlag(true);
        groupRepository.save(group);
    }

    public PageableListResponse<GroupResponse> getAllGroups(int page, int limit) {
        Pageable pageable = PageRequest.of(page-1, limit);
        List<Group> groups = groupRepository.findAllByDeleteFlagIsFalse(pageable).getContent();
        PageableListResponse<GroupResponse> response = new PageableListResponse<>();
        response.setPage(page);
        response.setLimit(limit);
        response.setListResults(convertListGroupToGroupResponse(groups));
        return response;
    }

    // convert list object from repository to Map
    private Map<Long, Long> mapListObjectToMap(List<Object[]> listObjects) {
        return listObjects.stream().collect(Collectors.toMap(
                row -> (Long) row[0], row -> (Long) row[1]
        ));
    }

    //convert list group from repository to list GroupResponse
    private List<GroupResponse> convertListGroupToGroupResponse(List<Group> groups) {
        List<Long> groupIds = groups.stream().map(Group::getGroupId).toList();
        Map<Long, Long> memberOfGroupId = mapListObjectToMap(groupMemberRepository.countMembersForGroupIds(groupIds));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return groups.stream().map(group -> {
            GroupResponse groupResponse = groupMapper.toGroupResponse(group);
            groupResponse.setCreatedDate(group.getCreateDate().toLocalDate().format(formatter));
            groupResponse.setTotalMembers(memberOfGroupId.getOrDefault(group.getGroupId(), 0L));
            return groupResponse;
        }).toList();
    }

    public PageableListResponse<GroupResponse> getListGroupsWithType(int page, int limit, Byte type) {
        Pageable pageable = PageRequest.of(page-1, limit);
        List<Group> groups = groupRepository.findAllByTypeAndDeleteFlagIsTrue(type, pageable).getContent();
        PageableListResponse<GroupResponse> response = new PageableListResponse<>();
        response.setPage(page);
        response.setLimit(limit);
        response.setListResults(convertListGroupToGroupResponse(groups));
        return response;
    }

    public PageableListResponse<GroupResponse> getListGroupsWithUserId(int page, int limit, Long userId) {
        Pageable pageable = PageRequest.of(page-1, limit);

        List<Group> listGroupJoined = groupRepository.findGroupsByUserId(userId, pageable).getContent();
        Collections.shuffle(listGroupJoined);
        PageableListResponse<GroupResponse> response = new PageableListResponse<>();
        response.setPage(page);
        response.setLimit(limit);
        response.setListResults(convertListGroupToGroupResponse(listGroupJoined));
        return response;
    }

    public GroupResponse getGroupById(Long groupId) {
        Group group = groupRepository.findGroupsByGroupIdAndDeleteFlagIsFalse(groupId)
                .orElseThrow(() -> new CustomException(ErrorCode.GROUP_NOT_FOUND));
        GroupResponse response = groupMapper.toGroupResponse(group);
        response.setTotalMembers(groupMemberRepository.countByGroup_GroupIdAndApprovedIsTrueAndDeleteFlagIsFalse(groupId));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        response.setCreatedDate(group.getCreateDate().toLocalDate().format(formatter));
        return response;
    }



}
