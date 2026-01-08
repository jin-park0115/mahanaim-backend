package com.mahanaim.mahanaim_backend.repository;

import com.mahanaim.mahanaim_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//회원 관리용 레포지토리
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
