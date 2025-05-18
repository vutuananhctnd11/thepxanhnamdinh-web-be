package com.graduate.be_txnd_fanzone.mapper;

import com.graduate.be_txnd_fanzone.dto.media.CreateMediaRequest;
import com.graduate.be_txnd_fanzone.dto.media.MediaResponse;
import com.graduate.be_txnd_fanzone.dto.media.UpdateMediaRequest;
import com.graduate.be_txnd_fanzone.dto.post.RepostRequest;
import com.graduate.be_txnd_fanzone.model.Media;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MediaMapper {

    Media toMedia(CreateMediaRequest request);

    MediaResponse toMediaResponse(Media media);

    @Mapping(target = "mediaId", ignore = true)
    Media updateMedia(UpdateMediaRequest request, @MappingTarget Media media);

    @Mapping(target = "mediaId", ignore = true)
    @Mapping(target = "post", ignore = true)
    Media rePostMedia(Media media);
}
