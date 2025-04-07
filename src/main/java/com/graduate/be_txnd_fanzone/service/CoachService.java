package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.coach.CoachInfoResponse;
import com.graduate.be_txnd_fanzone.dto.coach.CoachShortInfoResponse;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.mapper.CoachMapper;
import com.graduate.be_txnd_fanzone.model.Coach;
import com.graduate.be_txnd_fanzone.repository.CoachRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CoachService {

    CoachRepository coachRepository;

    CoachMapper coachMapper;

    public CoachInfoResponse getHeadCoachInfo() {
        Coach headCoach = coachRepository.findByPositionIgnoreCase("huấn luyện viên trưởng").orElseThrow(()
                -> new CustomException(ErrorCode.COACH_NOT_FOUND));
        return coachMapper.toCoachInfoResponse(headCoach);
    }

    public List<CoachShortInfoResponse> getAllCoach() {
        List<Coach> coachList = coachRepository.findAll();
        return coachList.stream().map(coachMapper::toCoachShortInfoResponse).toList();
    }
}
