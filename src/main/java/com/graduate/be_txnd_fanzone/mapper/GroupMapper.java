package com.graduate.be_txnd_fanzone.mapper;

import com.graduate.be_txnd_fanzone.dto.group.CreateGroupRequest;
import com.graduate.be_txnd_fanzone.dto.group.GroupResponse;
import com.graduate.be_txnd_fanzone.dto.group.UpdateGroupRequest;
import com.graduate.be_txnd_fanzone.model.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    Group toGroup(CreateGroupRequest request);

    GroupResponse toGroupResponse(Group group);

    @Mapping(target = "groupId", ignore = true)
    Group updateGroup(UpdateGroupRequest request,@MappingTarget Group group);
}
