package com.mahanaim.mahanaim_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VoteResultDto {
    private String candidateName;
    private Long voteCount;
}
