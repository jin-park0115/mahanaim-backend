package com.mahanaim.mahanaim_backend.controller;

import com.mahanaim.mahanaim_backend.dto.MatchRequestDto;
import com.mahanaim.mahanaim_backend.entity.Match;
import com.mahanaim.mahanaim_backend.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> create(@RequestBody MatchRequestDto dto){
        try{
            matchService.createMatch(dto);
            return ResponseEntity.ok("경기가 성공적으로 등록되었습니다.");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{matchId}/score")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateScore(
            @PathVariable Long matchId,
            @RequestBody Map<String, Integer> score) {
        matchService.updateMatchScore(matchId, score.get("homeScore"), score.get("awayScore"));
        return ResponseEntity.ok("점수가 업데이트되었습니다.");
    }

    @GetMapping
    public ResponseEntity<List<Match>> list() {
        return ResponseEntity.ok(matchService.getAllMatches());
    }
    @GetMapping("/main")
    public ResponseEntity<Map<String, Object>> getMainMatches() {
        return ResponseEntity.ok(matchService.getMainPageMatches());
    }
}
