package com.mahanaim.mahanaim_backend.repository;

import com.mahanaim.mahanaim_backend.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
    //    경기 일정 관리용
}
