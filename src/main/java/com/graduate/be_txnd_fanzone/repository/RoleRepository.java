package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);

    Optional<Role> findById(Integer roleId);

}
