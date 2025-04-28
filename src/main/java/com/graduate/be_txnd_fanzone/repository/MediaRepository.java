package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Long> {

    @Query("""
    SELECT m.mediaId FROM Media m
    WHERE\s
        m.post.postId = :postId
    \s""")
    List<Long> getListMediaIdsOfPost(@Param("postId") Long postId);

}
