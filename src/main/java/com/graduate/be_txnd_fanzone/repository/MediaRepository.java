package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Long> {

}
