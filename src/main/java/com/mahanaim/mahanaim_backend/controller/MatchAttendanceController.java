package com.mahanaim.mahanaim_backend.controller;


import com.mahanaim.mahanaim_backend.dto.MatchAttendanceRequestDto;
import com.mahanaim.mahanaim_backend.entity.MatchAttendance;
import com.mahanaim.mahanaim_backend.security.UserDetailsImpl;
import com.mahanaim.mahanaim_backend.service.MatchAttendanceService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class MatchAttendanceController {
    private final MatchAttendanceService attendanceService;

    @PostMapping("/vote")
    public ResponseEntity<String> vote(@RequestBody MatchAttendanceRequestDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        if (userDetails == null){
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        Long userId = userDetails.getUser().getUserId();

        try{
            String result = attendanceService.vote(userId, dto.getMatchId(), dto.getStatus());
            if("UPDATE".equals(result)){
                return ResponseEntity.ok("참석 정보가 수정되었습니다.");
            }
            return ResponseEntity.ok("참석 투표가 완료되었습니다.");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("실패: " + e.getMessage());
        }
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<List<MatchAttendance>> getAttendanceList(@PathVariable Long matchId){
        List<MatchAttendance> list = attendanceService.getAttendanceList(matchId);
        return ResponseEntity.ok(list);
    }
}
