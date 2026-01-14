package com.mahanaim.mahanaim_backend.service;

import com.mahanaim.mahanaim_backend.dto.MatchRequestDto;
import com.mahanaim.mahanaim_backend.entity.Match;
import com.mahanaim.mahanaim_backend.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchService {
    private final MatchRepository matchRepository;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Long createMatch(MatchRequestDto dto){
        if (dto.getMatchDate().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("과거 날짜로는 경기를 생성할 수 없습니다.");
        }
        Match match= Match.builder()
                .location(dto.getLocation())
                .matchDate(dto.getMatchDate())
                .description(dto.getDescription())
                .matchType(dto.getMatchType())
                .build();
        return matchRepository.save(match).getMatchId();
    }
    public List<Match> getAllMatches(){
        return matchRepository.findAll();
    }
}
