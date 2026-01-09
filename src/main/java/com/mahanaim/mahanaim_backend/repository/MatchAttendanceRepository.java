package com.mahanaim.mahanaim_backend.repository;

import com.mahanaim.mahanaim_backend.entity.Match;
import com.mahanaim.mahanaim_backend.entity.MatchAttendance;
import com.mahanaim.mahanaim_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchAttendanceRepository extends JpaRepository<MatchAttendance, Long> {
//    참가 신청 관리용. 누가 경기에 오는지 리스트를 뽑을 때.
//    특정 경기에 참가 신청한 모든 데이터 가져오기.
    List<MatchAttendance> findByMatch_MatchId(Long matchId);

    Long match(Match match);
    Optional<MatchAttendance> findByUserAndMatch(User user, Match match);
}
