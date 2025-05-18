package com.graduate.be_txnd_fanzone.mapper;

import com.graduate.be_txnd_fanzone.dto.club.ClubResponse;
import com.graduate.be_txnd_fanzone.dto.club.CreateClubRequest;
import com.graduate.be_txnd_fanzone.dto.club.UpdateClubRequest;
import com.graduate.be_txnd_fanzone.model.Club;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClubMapper {

    Club toClub (CreateClubRequest request);

    ClubResponse toClubResponse (Club club);

    void updateCLub (@MappingTarget Club club, UpdateClubRequest request);
}
