package com.mahanaim.mahanaim_backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteRequestDto {
    private Long matchId;
    private Long voterId;
    private Long candidateId;
}
