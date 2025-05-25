package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.PageableListResponse;
import com.graduate.be_txnd_fanzone.dto.group.CreateGroupRequest;
import com.graduate.be_txnd_fanzone.dto.group.GroupResponse;
import com.graduate.be_txnd_fanzone.dto.group.FanGroupResponse;
import com.graduate.be_txnd_fanzone.dto.group.UpdateGroupRequest;
import com.graduate.be_txnd_fanzone.dto.search.SearchGroupResponse;
import com.graduate.be_txnd_fanzone.dto.search.SearchRequest;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.mapper.GroupMapper;
import com.graduate.be_txnd_fanzone.model.Group;
import com.graduate.be_txnd_fanzone.repository.GroupMemberRepository;
import com.graduate.be_txnd_fanzone.repository.GroupRepository;
import com.graduate.be_txnd_fanzone.util.SecurityUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        group.setApproved(request.getType() != 2);
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
        Group groupUpdate = groupRepository.findByGroupIdAndApprovedIsTrueAndDeleteFlagIsFalse(request.getGroupId())
                .orElseThrow(() -> new CustomException(ErrorCode.GROUP_NOT_FOUND));
        groupUpdate = groupMapper.updateGroup(request,groupUpdate);
        groupRepository.save(groupUpdate);
        return groupMapper.toGroupResponse(groupUpdate);
    }

    public void softDeleteGroup(Long groupId) {
        Group group = groupRepository.findByGroupIdAndApprovedIsTrueAndDeleteFlagIsFalse(groupId)
                .orElseThrow(() -> new CustomException(ErrorCode.GROUP_NOT_FOUND));
        group.setDeleteFlag(true);
        groupRepository.save(group);
    }

    public PageableListResponse<GroupResponse> getAllGroups(int page, int limit) {
        Pageable pageable = PageRequest.of(page-1, limit);
        List<Group> groups = groupRepository.findAllByApprovedIsTrueAndDeleteFlagIsFalse(pageable).getContent();
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
        List<Group> groups = groupRepository.findAllByTypeAndApprovedIsTrueAndDeleteFlagIsTrue(type, pageable).getContent();
        PageableListResponse<GroupResponse> response = new PageableListResponse<>();
        response.setPage(page);
        response.setLimit(limit);
        response.setListResults(convertListGroupToGroupResponse(groups));
        return response;
    }

    public PageableListResponse<GroupResponse> getListGroupsWithUserId(int page, int limit, Long userId) {
        Pageable pageable = PageRequest.of(page-1, limit);

        List<Group> listGroupJoined = new ArrayList<>(groupRepository.findGroupsByUserId(userId, pageable).getContent());
        if (!listGroupJoined.isEmpty()) Collections.shuffle(listGroupJoined);
        PageableListResponse<GroupResponse> response = new PageableListResponse<>();
        response.setPage(page);
        response.setLimit(limit);
        response.setListResults(convertListGroupToGroupResponse(listGroupJoined));
        return response;
    }

    public GroupResponse getGroupById(Long groupId) {
        Long userLoginId = securityUtil.getCurrentUserId();
        Group group = groupRepository.findGroupsByGroupIdAndDeleteFlagIsFalse(groupId)
                .orElseThrow(() -> new CustomException(ErrorCode.GROUP_NOT_FOUND));
        GroupResponse response = groupMapper.toGroupResponse(group);
        response.setTotalMembers(groupMemberRepository.countByGroup_GroupIdAndApprovedIsTrueAndDeleteFlagIsFalse(groupId));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        response.setCreatedDate(group.getCreateDate().toLocalDate().format(formatter));
        boolean isSentJoinGroup = groupMemberRepository
                .findByUser_UserIdAndGroup_GroupIdAndApprovedIsFalseAndDeleteFlagIsFalse(userLoginId, groupId).isPresent();
        boolean isMemberOfGroup = groupMemberRepository
                .findByUser_UserIdAndGroup_GroupIdAndApprovedIsTrueAndDeleteFlagIsFalse(userLoginId, groupId).isPresent();
        if (isMemberOfGroup){
            response.setStatusMember((byte)2);
        } else {
            if(isSentJoinGroup) response.setStatusMember((byte)1);
            else response.setStatusMember((byte)0);
        }
        return response;
    }

    public PageableListResponse<SearchGroupResponse> searchGroups (SearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPage()-1, request.getLimit());
        PageableListResponse<SearchGroupResponse> response = new PageableListResponse<>();
        Long userLoginId = securityUtil.getCurrentUserId();

        Page<Group> groups = groupRepository.searchGroups(request.getSearch(), pageable);
        List<Long> groupIds = groups.getContent().stream().map(Group::getGroupId).toList();
        List<Long> joinedGroupIds = groupRepository.findGroupIdsByUserId(userLoginId);
        Map<Long, Long> memberOfGroupId = mapListObjectToMap(groupMemberRepository.countMembersForGroupIds(groupIds));

        List<SearchGroupResponse> groupsResponse = groups.getContent().stream().map(group -> {
            SearchGroupResponse groupResponse = groupMapper.toSearchGroupResponse(group);
            groupResponse.setTotalMembers(memberOfGroupId.getOrDefault(group.getGroupId(), 0L));
            groupResponse.setIsJoined(joinedGroupIds.contains(group.getGroupId()));
            return groupResponse;
        }).toList();
        response.setListResults(groupsResponse);
        response.setPage(request.getPage());
        response.setLimit(request.getLimit());
        response.setTotalPage((long) groups.getTotalPages());
        return response;
    }

    public PageableListResponse<FanGroupResponse> getListFanGroup(int page,int limit) {
        Pageable pageable = PageRequest.of(page-1, limit, Sort.by("createDate").descending());
        Page<Group> groups = groupRepository.findAllByTypeAndApprovedIsTrueAndDeleteFlagIsTrue((byte) 2, pageable);
        return convertToPageableFanGroupResponse(groups, page, limit);
    }

    public PageableListResponse<FanGroupResponse> getListCreateFansRequest (int page, int limit){
        Pageable pageable = PageRequest.of(page-1, limit, Sort.by("createDate").descending());
        Page<Group> groups = groupRepository.findFansGroupRequest(pageable);
        return convertToPageableFanGroupResponse(groups, page, limit);
    }

    private PageableListResponse<FanGroupResponse> convertToPageableFanGroupResponse (Page<Group> groups, int page, int limit){
        PageableListResponse<FanGroupResponse> response = new PageableListResponse<>();
        response.setPage(page);
        response.setLimit(limit);
        response.setTotalPage((long) groups.getTotalPages());
        response.setListResults(groups.getContent().stream().map(groupMapper::toFanGroupResponse).collect(Collectors.toList()));
        return response;
    }

    public void approveFanGroupRequest(Long groupId) {
        Group group = groupRepository.findByGroupIdAndApprovedIsFalseAndDeleteFlagIsFalse(groupId)
                .orElseThrow(() -> new CustomException(ErrorCode.GROUP_NOT_FOUND));
        group.setApproved(true);
        groupRepository.save(group);
    }

    public void rejectFanGroupRequest(Long groupId) {
        Group group = groupRepository.findByGroupIdAndApprovedIsFalseAndDeleteFlagIsFalse(groupId)
                .orElseThrow(() -> new CustomException(ErrorCode.GROUP_NOT_FOUND));
        group.setApproved(false);
        groupRepository.save(group);
    }
}
