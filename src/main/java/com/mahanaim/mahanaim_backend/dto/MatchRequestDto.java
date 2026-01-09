package com.mahanaim.mahanaim_backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MatchRequestDto {
    private LocalDateTime matchDate;
    private String matchType;
    private String description;
    private String location;
}
