package com.mahanaim.mahanaim_backend.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_mom_vote_match_voter", columnNames = {"match_id", "voter_id"})
})
public class MomVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voter_id")
    private User voter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private User candidate;

    private LocalDateTime votedAt;

    @Builder
    public MomVote(Match match, User voter, User candidate){
        if (voter == null || candidate == null) {
            throw new IllegalArgumentException("투표자와 대상자는 필수입니다.");
        }

        if (voter.getUserId().equals(candidate.getUserId())) {
            throw new IllegalArgumentException("본인에게는 투표할 수 없습니다.");
        }

        this.match = match;
        this.voter = voter;
        this.candidate = candidate;
        this.votedAt = LocalDateTime.now(); // 생성 시점에 시간 기록
    }
}
