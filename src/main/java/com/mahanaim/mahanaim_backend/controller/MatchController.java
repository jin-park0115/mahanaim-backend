package com.mahanaim.mahanaim_backend.controller;

import com.mahanaim.mahanaim_backend.dto.MatchRequestDto;
import com.mahanaim.mahanaim_backend.entity.Match;
import com.mahanaim.mahanaim_backend.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<Match>> list() {
        return ResponseEntity.ok(matchService.getAllMatches());
    }
}
