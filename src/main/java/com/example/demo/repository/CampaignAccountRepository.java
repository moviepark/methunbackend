package com.example.demo.repository;

import com.example.demo.model.CampaignAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CampaignAccountRepository extends JpaRepository<CampaignAccount, Long> {
    Optional<CampaignAccount> findByEmail(String email);
    boolean existsByEmail(String email);
    List<CampaignAccount> findByDomainRole(String domainRole);
}
