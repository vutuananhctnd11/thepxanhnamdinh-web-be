package com.graduate.be_txnd_fanzone.mapper;

import com.graduate.be_txnd_fanzone.dto.groupMember.AddGroupMemberRequest;
import com.graduate.be_txnd_fanzone.dto.groupMember.GroupMemberResponse;
import com.graduate.be_txnd_fanzone.model.GroupMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupMemberMapper {

    GroupMember toGroupMember(AddGroupMemberRequest request);

    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "groupId", source = "group.groupId")
    @Mapping(target = "groupName", source = "group.groupName")
    GroupMemberResponse toGroupMemberResponse(GroupMember groupMember);
}
