package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.PageableListResponse;
import com.graduate.be_txnd_fanzone.dto.club.ClubResponse;
import com.graduate.be_txnd_fanzone.dto.club.CreateClubRequest;
import com.graduate.be_txnd_fanzone.dto.club.UpdateClubRequest;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.mapper.ClubMapper;
import com.graduate.be_txnd_fanzone.model.Club;
import com.graduate.be_txnd_fanzone.repository.ClubRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClubService {

    ClubRepository clubRepository;
    ClubMapper clubMapper;

    public ClubResponse createClub (CreateClubRequest request) {
        Club club = clubMapper.toClub(request);
        clubRepository.save(club);
        return clubMapper.toClubResponse(club);
    }

    public ClubResponse updateClub (UpdateClubRequest request) {
        Club club = clubRepository.findByClubIdAndDeleteFlagIsFalse(request.getClubId())
                .orElseThrow(() -> new CustomException(ErrorCode.CLUB_NOT_FOUND));
        clubMapper.updateCLub(club, request);
        clubRepository.save(club);
        return clubMapper.toClubResponse(club);
    }

    public void deleteClub (Long clubId) {
        Club club = clubRepository.findByClubIdAndDeleteFlagIsFalse(clubId)
                .orElseThrow(() -> new CustomException(ErrorCode.CLUB_NOT_FOUND));
        if (!club.getAllowDelete()){
            return;
        }
        club.setDeleteFlag(true);
    }

    public PageableListResponse<ClubResponse> getListClub (int page, int limit) {
        PageableListResponse<ClubResponse> response = new PageableListResponse<>();
        Pageable pageable = PageRequest.of(page-1, limit, Sort.by("clubName").ascending());
        Page<Club> clubs = clubRepository.findAllByAllowDeleteIsTrue(pageable);
        List<ClubResponse> clubResponses = clubs.getContent().stream().map(clubMapper::toClubResponse).toList();
        response.setListResults(clubResponses);
        response.setPage(page);
        response.setLimit(limit);
        response.setTotalPage((long) clubs.getTotalPages());
        return response;
    }

    public ClubResponse getMyClub () {
        Club club = clubRepository.findByAllowDeleteIsFalse()
                .orElseThrow(() -> new CustomException(ErrorCode.CLUB_NOT_FOUND));
        return clubMapper.toClubResponse(club);
    }

    public List<ClubResponse> getListClubToFill () {
        List<Club> clubs = clubRepository.findAllByDeleteFlagIsFalse();
        return clubs.stream().map(clubMapper::toClubResponse).toList();
    }
}
