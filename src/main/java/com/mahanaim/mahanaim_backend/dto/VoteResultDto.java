package com.mahanaim.mahanaim_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
// 서버 -> 클라이언트
public class VoteResultDto {
    private String candidateName;
    private Long voteCount;
}
