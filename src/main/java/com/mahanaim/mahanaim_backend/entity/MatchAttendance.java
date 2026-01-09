package com.mahanaim.mahanaim_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MatchAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    private LocalDateTime appliedAt = LocalDateTime.now();

    public void updateStatus(AttendanceStatus status){
        this.status = status;
        this.appliedAt = LocalDateTime.now();
    }

    public MatchAttendance(User user, Match match, AttendanceStatus status){
        this.user = user;
        this.match = match;
        this.status = status;
        this.appliedAt = LocalDateTime.now();
    }

}
