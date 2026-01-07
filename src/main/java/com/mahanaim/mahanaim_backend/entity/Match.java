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
    private String Location;

    private String matchType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Builder
    public Match(LocalDateTime matchDate, String location, String matchType, String description){
        this.matchDate = matchDate;
        this.Location = location;
        this.matchType = matchType;
        this.description = description;
    }
}
