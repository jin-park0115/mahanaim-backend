package com.mahanaim.mahanaim_backend.repository;

import com.mahanaim.mahanaim_backend.dto.VoteResultDto;
import com.mahanaim.mahanaim_backend.entity.MomVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MomVoteRepository extends JpaRepository<MomVote, Long> {
    boolean existsByMatch_MatchIdAndVoter_UserId(Long matchId, Long voterId);

    @Query("SELECT new com.mahanaim.mahanaim_backend.dto.VoteResultDto(v.candidate.name, COUNT(v)) " +
            "FROM MomVote v " +
            "WHERE v.match.matchId = :matchId " +
            "GROUP BY v.candidate.name " +
            "ORDER BY COUNT(v) DESC")
    List<VoteResultDto> findVoteResultByMatchId(@Param("matchId") Long matchId);
}
