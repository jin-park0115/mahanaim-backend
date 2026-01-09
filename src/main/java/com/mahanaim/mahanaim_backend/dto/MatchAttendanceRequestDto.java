package com.mahanaim.mahanaim_backend.dto;

import com.mahanaim.mahanaim_backend.entity.AttendanceStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MatchAttendanceRequestDto {
    private Long matchId;
    private AttendanceStatus status; //   ATTEND, ABSENT, PENDING
}
