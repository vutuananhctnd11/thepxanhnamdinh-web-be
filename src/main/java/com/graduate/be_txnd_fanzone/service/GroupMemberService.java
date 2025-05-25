package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.PageableListResponse;
import com.graduate.be_txnd_fanzone.dto.groupMember.AddGroupMemberRequest;
import com.graduate.be_txnd_fanzone.dto.groupMember.ChangeMemberRoleRequest;
import com.graduate.be_txnd_fanzone.dto.groupMember.CheckIsMemberResponse;
import com.graduate.be_txnd_fanzone.dto.groupMember.GroupMemberResponse;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.mapper.GroupMemberMapper;
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
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GroupMemberService {

    GroupMemberRepository groupMemberRepository;
    UserRepository userRepository;
    GroupRepository groupRepository;
    GroupMemberMapper groupMemberMapper;
    SecurityUtil securityUtil;

    public GroupMemberResponse joinGroupRequest(AddGroupMemberRequest request) {
        User user = userRepository.findByUserIdAndDeleteFlagIsFalse(request.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Group group = groupRepository.findByGroupIdAndApprovedIsTrueAndDeleteFlagIsFalse(request.getGroupId())
                .orElseThrow(() -> new CustomException(ErrorCode.GROUP_NOT_FOUND));

        //check is member
        boolean isMember = groupMemberRepository
                .existsByUser_UserIdAndGroup_GroupIdAndApprovedIsTrueAndDeleteFlagIsFalse(request.getUserId(), request.getGroupId());
        if (isMember) {
            throw new CustomException(ErrorCode.MEMBER_EXISTED);
        }

        GroupMember groupMember = new GroupMember();
        groupMember.setUser(user);
        groupMember.setGroup(group);
        groupMember.setMemberRole((byte) 0);
        groupMember.setApproved(!group.getCensorMember());
        groupMember = groupMemberRepository.save(groupMember);

        GroupMemberResponse response = groupMemberMapper.toGroupMemberResponse(groupMember);
        PrettyTime prettyTime = new PrettyTime(Locale.forLanguageTag("vi"));
        response.setRequestAt(prettyTime.format(groupMember.getCreateDate()));
        return response;
    }

    public void deleteGroupMember(Long groupId, Long userId) {
        GroupMember groupMember = groupMemberRepository
                .findByUser_UserIdAndGroup_GroupIdAndApprovedIsTrueAndDeleteFlagIsFalse(userId, groupId)
                .orElseThrow(() -> new CustomException(ErrorCode.GROUP_MEMBER_NOT_FOUND));
        groupMemberRepository.delete(groupMember);
    }

    public void deleteJoinGroupRequest(Long groupId, Long userId) {
        GroupMember groupMember = groupMemberRepository
                .findByUser_UserIdAndGroup_GroupIdAndApprovedIsFalseAndDeleteFlagIsFalse(userId, groupId)
                .orElseThrow(() -> new CustomException(ErrorCode.GROUP_MEMBER_NOT_FOUND));
        groupMemberRepository.delete(groupMember);
    }

    public void addAdminGroup(String username, Long groupId) {
        User user = userRepository.findByUsernameAndDeleteFlagIsFalse(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Group group = groupRepository.findByGroupIdAndApprovedIsTrueAndDeleteFlagIsFalse(groupId)
                .orElseThrow(() -> new CustomException(ErrorCode.GROUP_NOT_FOUND));

        GroupMember groupMember = new GroupMember();
        groupMember.setUser(user);
        groupMember.setGroup(group);
        groupMember.setMemberRole((byte) 2);
        groupMember.setApproved(true);

        groupMemberRepository.save(groupMember);
    }

    public void approveMember (Long groupMemberId) {
        GroupMember groupMember = groupMemberRepository.findByGroupMemberIdAndDeleteFlagIsFalse(groupMemberId)
                .orElseThrow(() -> new CustomException(ErrorCode.GROUP_MEMBER_NOT_FOUND));

        //check role or user approve
        Long groupId = groupMember.getGroup().getGroupId();
        Long userLoginId = securityUtil.getCurrentUserId();
        boolean isAdminGroup = groupMemberRepository
                .existsByUser_UserIdAndGroup_GroupIdAndMemberRoleAndDeleteFlagIsFalse(userLoginId, groupId, (byte) 2);
        boolean isModeratorGroup = groupMemberRepository
                .existsByUser_UserIdAndGroup_GroupIdAndMemberRoleAndDeleteFlagIsFalse(userLoginId, groupId, (byte) 1);

        if (isAdminGroup || isModeratorGroup) {
            groupMember.setApproved(true);
            groupMemberRepository.save(groupMember);
        } else {
            throw new CustomException(ErrorCode.NO_PERMISSION);
        }
    }

    public CheckIsMemberResponse isMember (Long userId, Long groupId) {
        CheckIsMemberResponse response = new CheckIsMemberResponse();
        boolean checkMember = groupMemberRepository
                .existsByUser_UserIdAndGroup_GroupIdAndApprovedIsTrueAndDeleteFlagIsFalse(userId, groupId);
        if (checkMember) {
            GroupMember groupMember = groupMemberRepository
                    .findByUser_UserIdAndGroup_GroupIdAndApprovedIsTrueAndDeleteFlagIsFalse(userId, groupId)
                    .orElseThrow(() -> new CustomException(ErrorCode.GROUP_MEMBER_NOT_FOUND));
            response.setMemberRole(groupMember.getMemberRole());
        }
        response.setIsMember(checkMember);
        return response;
    }

    public PageableListResponse<GroupMemberResponse> getListMember (int page, int limit, Long groupId) {
        Pageable pageable = PageRequest.of(page-1, limit);
        Page<GroupMember> groupMemberPage = groupMemberRepository
                .findByGroup_GroupIdAndApprovedIsTrueAndDeleteFlagIsFalse(groupId, pageable);

        return convertToResponse(page, limit, groupMemberPage);
    }

    private PageableListResponse<GroupMemberResponse> convertToResponse (int page, int limit, Page<GroupMember> listPage) {
        PageableListResponse<GroupMemberResponse> response = new PageableListResponse<>();
        PrettyTime prettyTime = new PrettyTime(Locale.forLanguageTag("vi"));
        List<GroupMemberResponse> memberResponseList = listPage.getContent().stream().map(groupMember -> {
            GroupMemberResponse groupMemberResponse = groupMemberMapper.toGroupMemberResponse(groupMember);
            groupMemberResponse.setRequestAt(prettyTime.format(groupMember.getCreateDate()));
            return groupMemberResponse;
        }).toList();
        response.setListResults(memberResponseList);
        response.setPage(page);
        response.setLimit(limit);
        response.setTotalPage(listPage.getTotalElements());
        return response;
    }

    public PageableListResponse<GroupMemberResponse> getListGroupManager (int page, int limit, Long groupId) {
        PageableListResponse<GroupMemberResponse> response = new PageableListResponse<>();
        Pageable pageable = PageRequest.of(page-1, limit);
        Page<GroupMember> groupMemberPage = groupMemberRepository.findGroupManager(groupId, pageable);

        return convertToResponse(page, limit, groupMemberPage);
    }

    public PageableListResponse<GroupMemberResponse> getListRequestJoinedGroup (int page, int limit, Long groupId) {
        PageableListResponse<GroupMemberResponse> response = new PageableListResponse<>();
        Pageable pageable = PageRequest.of(page-1, limit);
        Page<GroupMember> groupMemberList = groupMemberRepository
                .findAllByGroup_GroupIdAndApprovedIsFalseAndDeleteFlagIsFalseOrderByCreateDateDesc(groupId, pageable);
        PrettyTime prettyTime = new PrettyTime(Locale.forLanguageTag("vi"));
        response.setListResults(groupMemberList.getContent().stream().map(groupMember -> {
            GroupMemberResponse groupMemberResponse = groupMemberMapper.toGroupMemberResponse(groupMember);
            groupMemberResponse.setRequestAt(prettyTime.format(groupMember.getCreateDate()));
            return groupMemberResponse;
        }).toList());
        response.setPage(page);
        response.setLimit(limit);
        response.setTotalPage((long) groupMemberList.getTotalPages());
        return response;
    }

    public void changeMemberRole (ChangeMemberRoleRequest request) {
        GroupMember groupMember = groupMemberRepository.findByUser_UserIdAndGroup_GroupIdAndApprovedIsFalseAndDeleteFlagIsFalse(request.getUserId(), request.getGroupId())
                .orElseThrow(() -> new CustomException(ErrorCode.GROUP_MEMBER_NOT_FOUND));
        groupMember.setMemberRole(request.getMemberRole());
        groupMemberRepository.save(groupMember);

    }
}
