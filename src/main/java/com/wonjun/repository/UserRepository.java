package com.wonjun.repository;

import com.wonjun.model.entity.BoardUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<BoardUser, Long> {
    Optional<BoardUser> findByUsername(String username);
}
