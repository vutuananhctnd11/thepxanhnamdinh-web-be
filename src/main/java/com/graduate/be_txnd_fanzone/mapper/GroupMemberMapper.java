package com.graduate.be_txnd_fanzone.mapper;

import com.graduate.be_txnd_fanzone.dto.groupMember.AddGroupMemberRequest;
import com.graduate.be_txnd_fanzone.dto.groupMember.GroupMemberResponse;
import com.graduate.be_txnd_fanzone.model.GroupMember;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMemberMapper {

    GroupMember toGroupMember(AddGroupMemberRequest request);

    GroupMemberResponse toGroupMemberResponse(GroupMember groupMember);
}
