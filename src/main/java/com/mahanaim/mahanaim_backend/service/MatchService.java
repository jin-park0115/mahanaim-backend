package com.mahanaim.mahanaim_backend.service;

import com.mahanaim.mahanaim_backend.dto.MatchRequestDto;
import com.mahanaim.mahanaim_backend.entity.Match;
import com.mahanaim.mahanaim_backend.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
                .endTime(dto.getEndTime())
                .description(dto.getDescription())
                .matchType(dto.getMatchType())
                .build();
        return matchRepository.save(match).getMatchId();
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void updateMatchScore(Long matchId, Integer homeScore, Integer awayScore){
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("해당 경기가 존재하지 않습니다."));
        match.updateScore(homeScore, awayScore);
    }

    public List<Match> getAllMatches(){
        return matchRepository.findAll();
    }

    public Map<String, Object> getMainPageMatches() {
        LocalDateTime now = LocalDateTime.now();

        List<Match> upcomingMatches = matchRepository.findAllByMatchDateAfterOrderByMatchDateAsc(now);
        Optional<Match> lastMatch = matchRepository.findFirstByMatchDateBeforeOrderByMatchDateDesc(now);

        Map<String, Object> result = new HashMap<>();
        result.put("upcomingMatches", upcomingMatches);
        result.put("lastMatch", lastMatch.orElse(null));

        return result;
    }
}
