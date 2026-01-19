package com.mahanaim.mahanaim_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private LocalDateTime endTime;
    @Column(nullable = false)
    private String location;

    private String matchType;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer homeScore;
    private Integer awayScore;

    @JsonIgnoreProperties("match")
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MatchAttendance> attendances = new ArrayList<>();

    @Builder
    public Match(LocalDateTime matchDate, LocalDateTime endTime,String location, String matchType, String description){
        this.matchDate = matchDate;
        this.endTime = endTime;
        this.location = location;
        this.matchType = matchType;
        this.description = description;
    }

    public void updateScore(Integer homeScore, Integer awayScore){
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }
}
