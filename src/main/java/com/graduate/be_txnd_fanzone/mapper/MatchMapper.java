package com.graduate.be_txnd_fanzone.mapper;

import com.graduate.be_txnd_fanzone.dto.match.MatchInfoResponse;
import com.graduate.be_txnd_fanzone.model.Match;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MatchMapper {

    @Mapping(source = "homeClub.clubName", target = "homeName")
    @Mapping(source = "homeClub.logo", target = "homeLogo")
    @Mapping(source = "awayClub.clubName", target = "awayName")
    @Mapping(source = "awayClub.logo", target = "awayLogo")
    @Mapping(source = "homeClub.stadium", target = "stadium")
    MatchInfoResponse toMatchInfoResponse (Match match);
}
