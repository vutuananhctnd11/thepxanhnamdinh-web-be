package com.graduate.be_txnd_fanzone.mapper;

import com.graduate.be_txnd_fanzone.dto.media.CreateMediaRequest;
import com.graduate.be_txnd_fanzone.dto.media.MediaResponse;
import com.graduate.be_txnd_fanzone.dto.media.UpdateMediaRequest;
import com.graduate.be_txnd_fanzone.model.Media;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MediaMapper {

    Media toMedia(CreateMediaRequest request);

    MediaResponse toMediaResponse(Media media);

    Media updateMedia(UpdateMediaRequest request, @MappingTarget Media media);
}
