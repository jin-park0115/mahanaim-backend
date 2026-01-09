package com.mahanaim.mahanaim_backend.service;

import com.mahanaim.mahanaim_backend.entity.AttendanceStatus;
import com.mahanaim.mahanaim_backend.entity.Match;
import com.mahanaim.mahanaim_backend.entity.MatchAttendance;
import com.mahanaim.mahanaim_backend.entity.User;
import com.mahanaim.mahanaim_backend.repository.MatchAttendanceRepository;
import com.mahanaim.mahanaim_backend.repository.MatchRepository;
import com.mahanaim.mahanaim_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchAttendanceService {
    private final MatchAttendanceRepository matchAttendanceRepository;
    private final UserRepository userRepository;
    private final MatchRepository matchRepository;

    public String vote(Long userId, Long matchId, AttendanceStatus status){
        User user = userRepository.findById(userId).orElseThrow();
        Match match = matchRepository.findById(matchId).orElseThrow();

        Optional<MatchAttendance> existingAttendance = matchAttendanceRepository.findByUserAndMatch(user, match);

        if (existingAttendance.isPresent()) {
            MatchAttendance attendance = existingAttendance.get();
            attendance.updateStatus(status);
            matchAttendanceRepository.save(attendance);
            return "UPDATE"; // 수정됨
        } else {
            MatchAttendance attendance = new MatchAttendance(user, match, status);
            matchAttendanceRepository.save(attendance);
            return "CREATE"; // 신규 등록
        }
    }

    public List<MatchAttendance> getAttendanceList(Long matchId){
        return matchAttendanceRepository.findByMatch_MatchId(matchId);
    }
}
