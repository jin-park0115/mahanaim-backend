package com.mahanaim.mahanaim_backend.repository;

import com.mahanaim.mahanaim_backend.entity.MomVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MomVoteRepository extends JpaRepository<MomVote, Long> {
    boolean existsByMatch_MatchIdAndVoter_UserId(Long matchId, Long voterId);
}
