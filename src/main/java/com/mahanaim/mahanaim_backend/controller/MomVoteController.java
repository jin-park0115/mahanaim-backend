package com.mahanaim.mahanaim_backend.controller;

import com.mahanaim.mahanaim_backend.dto.VoteRequestDto;
import com.mahanaim.mahanaim_backend.dto.VoteResultDto;
import com.mahanaim.mahanaim_backend.service.MomVoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class MomVoteController {
    private final MomVoteService momVoteService;

    @PostMapping
    public ResponseEntity<String> vote(@RequestBody VoteRequestDto requestDto){
        try{
            momVoteService.vote(
                    requestDto.getMatchId(),
                    requestDto.getVoterId(),
                    requestDto.getCandidateId()
            );
            return ResponseEntity.ok("투표 완료!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{matchId}/results")
    public ResponseEntity<List<VoteResultDto>> getResults(@PathVariable Long matchId){
        return ResponseEntity.ok(momVoteService.getVoteResults(matchId));
    }
}
