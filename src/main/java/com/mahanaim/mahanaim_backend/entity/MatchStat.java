package com.mahanaim.mahanaim_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "match_stats")

public class MatchStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private int goals;
    private int assists;

    @Setter
    private boolean isMom;

    @Builder
    public MatchStat(Match match, User user, int goals, int assists, boolean isMom){
        this.match = match;
        this.user = user;
        this.goals = goals;
        this.assists = assists;
        this.isMom = isMom;
    }

    public void markAsMom() {
        this.isMom = true;
    }
}
