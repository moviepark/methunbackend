package com.example.demo.service;

import com.example.demo.model.CampaignAccount;
import com.example.demo.repository.CampaignAccountRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final CampaignAccountRepository campaignAccountRepository;

    public CustomUserDetailsService(CampaignAccountRepository campaignAccountRepository) {
        this.campaignAccountRepository = campaignAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CampaignAccount account = campaignAccountRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found: " + username));

        return new org.springframework.security.core.userdetails.User(
                account.getEmail(),
                account.getPasswordHash(),
                account.isActive(),
                true,
                true,
                true,
                List.of(new SimpleGrantedAuthority(account.getDomainRole()))
        );
    }
}
