package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByPostIdAndDeleteFlagIsFalse(Long postId);
}
