package com.graduate.be_txnd_fanzone.mapper;

import com.graduate.be_txnd_fanzone.dto.coach.CoachInfoResponse;
import com.graduate.be_txnd_fanzone.dto.coach.CoachShortInfoResponse;
import com.graduate.be_txnd_fanzone.model.Coach;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoachMapper {

    CoachInfoResponse toCoachInfoResponse (Coach coach);
    CoachShortInfoResponse toCoachShortInfoResponse (Coach coach);
}
