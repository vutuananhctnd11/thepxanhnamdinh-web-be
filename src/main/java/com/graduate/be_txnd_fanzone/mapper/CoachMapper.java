package com.graduate.be_txnd_fanzone.mapper;

import com.graduate.be_txnd_fanzone.dto.coach.CoachInfoResponse;
import com.graduate.be_txnd_fanzone.dto.coach.CoachShortInfoResponse;
import com.graduate.be_txnd_fanzone.dto.coach.CreateCoachRequest;
import com.graduate.be_txnd_fanzone.dto.coach.UpdateCoachRequest;
import com.graduate.be_txnd_fanzone.model.Coach;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CoachMapper {

    CoachInfoResponse toCoachInfoResponse (Coach coach);

    CoachShortInfoResponse toCoachShortInfoResponse (Coach coach);

    Coach toCoach (CreateCoachRequest request);

    void updateCoach (@MappingTarget Coach coach, UpdateCoachRequest request);
}
