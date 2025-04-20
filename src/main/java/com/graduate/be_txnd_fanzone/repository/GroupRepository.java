package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findByGroupIdAndDeleteFlagIsFalse(Long groupId);

    Page<Group> findAllByDeleteFlagIsFalse(Pageable pageable);
}
