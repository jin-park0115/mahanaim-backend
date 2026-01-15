package com.mahanaim.mahanaim_backend.repository;

import com.mahanaim.mahanaim_backend.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match, Long> {
    //    경기 일정 관리용
    // 다가오는 경기들: 현재 시간 이후의 경기들을 날짜순으로 정렬
    List<Match> findAllByMatchDateAfterOrderByMatchDateAsc(LocalDateTime dateTime);
    // 가장 최근 경기: 현재 시간 이전의 경기 중 가장 가까운 날짜 하나만 가져옴
    Optional<Match> findFirstByMatchDateBeforeOrderByMatchDateDesc(LocalDateTime dateTime);
}
