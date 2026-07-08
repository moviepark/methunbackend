package com.example.demo.repository;

import com.example.demo.model.VoterProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface VoterProfileRepository extends JpaRepository<VoterProfile, Long> {
    List<VoterProfile> findByElectoralDistrict(String electoralDistrict);
    List<VoterProfile> findByEngagementStatus(VoterProfile.EngagementStatus status);

    @Query("SELECT AVG(v.supportScore) FROM VoterProfile v WHERE v.electoralDistrict = :district")
    Double getAverageSupportScoreByDistrict(@Param("district") String district);

    @Query("SELECT AVG(v.supportScore) FROM VoterProfile v")
    Double getOverallAverageSupportScore();

    long countByElectoralDistrict(String electoralDistrict);
}
