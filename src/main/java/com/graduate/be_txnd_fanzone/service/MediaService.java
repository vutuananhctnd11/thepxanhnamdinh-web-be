package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.media.CreateMediaRequest;
import com.graduate.be_txnd_fanzone.dto.media.UpdateMediaRequest;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.mapper.MediaMapper;
import com.graduate.be_txnd_fanzone.model.Media;
import com.graduate.be_txnd_fanzone.model.Post;
import com.graduate.be_txnd_fanzone.repository.MediaRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MediaService {

    MediaMapper mediaMapper;
    MediaRepository mediaRepository;

    public Media createMedia(CreateMediaRequest createMediaRequest) {
        Media media = mediaMapper.toMedia(createMediaRequest);
        return mediaRepository.save(media);
    }

    public Media updateMedia(UpdateMediaRequest request) {
        Media media = mediaRepository.findById(request.getMediaId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEDIA_NOT_FOUND));
        media = mediaMapper.updateMedia(request, media);
        return mediaRepository.save(media);
    }

    public void deleteMedia(Long mediaId) {
        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEDIA_NOT_FOUND));
        mediaRepository.delete(media);
    }

    public List<Media> rePostMedia(List<Media> medias, Post post) {
        return medias.stream().map(media -> {
            Media cloneMedia = mediaMapper.rePostMedia(media);
            cloneMedia.setPost(post);
            return mediaRepository.save(cloneMedia);
        }).collect(Collectors.toList());
    }
}
