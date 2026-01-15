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
@Table(name = "matches")

public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;

    @Column(nullable = false)
    private LocalDateTime matchDate;
    @Column(nullable = false)
    private String location;

    private String matchType;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer homeScore;
    private Integer awayScore;

    @Builder
    public Match(LocalDateTime matchDate, String location, String matchType, String description){
        this.matchDate = matchDate;
        this.location = location;
        this.matchType = matchType;
        this.description = description;
    }

    public void updateScore(Integer homeScore, Integer awayScore){
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }
}
