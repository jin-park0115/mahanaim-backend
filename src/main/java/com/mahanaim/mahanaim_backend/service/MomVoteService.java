package com.mahanaim.mahanaim_backend.service;

import com.mahanaim.mahanaim_backend.entity.Match;
import com.mahanaim.mahanaim_backend.entity.MomVote;
import com.mahanaim.mahanaim_backend.entity.User;
import com.mahanaim.mahanaim_backend.repository.MatchRepository;
import com.mahanaim.mahanaim_backend.repository.MomVoteRepository;
import com.mahanaim.mahanaim_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MomVoteService {
    private final MomVoteRepository momVoteRepository;
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long vote(Long matchId, Long voterId, Long candidateId){
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("해당 경기가 존재하지 않습니다."));
        User voter = userRepository.findById(voterId)
                .orElseThrow(() -> new IllegalArgumentException("투표자가 존재하지 않습니다."));
        User candidate = userRepository.findById(candidateId)
                .orElseThrow(() -> new IllegalArgumentException("투표 대상자가 존재하지 않습니다."));
        // 본인 투표 금지 로직
        if (voterId.equals(candidateId)){
            throw new IllegalStateException("본인에게는 투표할 수 없습니다.");
        }
        // 중복 투표 방지 로직
        if (momVoteRepository.existsByMatch_MatchIdAndVoter_UserId(matchId, voterId)){
            throw new IllegalStateException("이미 이 경기에 투표하셨습니다.");
        }
        // 24시간 기한 체크 로직
        if (match.getMatchDate().plusDays(1).isBefore(LocalDateTime.now())){
            throw new IllegalStateException("투표 기한(경기 후 24시간)이 지났습니다.");
        }
        MomVote momvote = MomVote.builder()
                .match(match)
                .voter(voter)
                .candidate(candidate)
                .build();
        return momVoteRepository.save(momvote).getVoteId();
    }
}
