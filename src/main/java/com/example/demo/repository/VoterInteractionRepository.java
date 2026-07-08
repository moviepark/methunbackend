package com.example.demo.repository;

import com.example.demo.model.VoterInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface VoterInteractionRepository extends JpaRepository<VoterInteraction, Long> {
    List<VoterInteraction> findByVoterVoterId(Long voterId);
    List<VoterInteraction> findByVolunteerAccountId(Long accountId);

    @Query("SELECT i FROM VoterInteraction i ORDER BY i.loggedAt DESC")
    List<VoterInteraction> findRecentInteractions();

    @Query("SELECT i.sentimentDetected, COUNT(i) FROM VoterInteraction i GROUP BY i.sentimentDetected")
    List<Object[]> countInteractionsBySentiment();
}
