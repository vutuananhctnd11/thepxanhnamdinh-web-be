package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.coach.CoachInfoResponse;
import com.graduate.be_txnd_fanzone.dto.coach.CoachShortInfoResponse;
import com.graduate.be_txnd_fanzone.dto.coach.CreateCoachRequest;
import com.graduate.be_txnd_fanzone.dto.coach.UpdateCoachRequest;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.mapper.CoachMapper;
import com.graduate.be_txnd_fanzone.model.Club;
import com.graduate.be_txnd_fanzone.model.Coach;
import com.graduate.be_txnd_fanzone.repository.ClubRepository;
import com.graduate.be_txnd_fanzone.repository.CoachRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CoachService {

    CoachRepository coachRepository;
    ClubRepository clubRepository;
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

    public CoachInfoResponse createCoach (CreateCoachRequest request) {
        if (request.getPosition().equalsIgnoreCase("huấn luyện viên trưởng")){
            throw new CustomException(ErrorCode.HEAD_COACH_INVALID);
        }
        Club myClub = clubRepository.findByAllowDeleteIsFalse()
                .orElseThrow(() -> new CustomException(ErrorCode.CLUB_NOT_FOUND));
        Coach coach = coachMapper.toCoach(request);
        coach.setClub(myClub);
        coach.setAge((byte) Period.between(coach.getDateOfBirth(), LocalDate.now()).getYears());
        coachRepository.save(coach);
        return coachMapper.toCoachInfoResponse(coach);
    }

    public CoachInfoResponse updateCoach (UpdateCoachRequest request) {
        Coach coach = coachRepository.findByCoachIdAndDeleteFlagIsFalse(request.getCoachId())
                .orElseThrow(() -> new CustomException(ErrorCode.COACH_NOT_FOUND));
        coachMapper.updateCoach(coach, request);
        coachRepository.save(coach);
        return coachMapper.toCoachInfoResponse(coach);
    }

    public void deleteCoach (Long coachId) {
        Coach coach = coachRepository.findByCoachIdAndDeleteFlagIsFalse(coachId)
                .orElseThrow(() -> new CustomException(ErrorCode.COACH_NOT_FOUND));
        coach.setDeleteFlag(true);
    }

    public CoachInfoResponse getCoachInfoById (Long coachId) {
        Coach coach = coachRepository.findByCoachIdAndDeleteFlagIsFalse(coachId)
                .orElseThrow(() -> new CustomException(ErrorCode.COACH_NOT_FOUND));
        return coachMapper.toCoachInfoResponse(coach);
    }
}
