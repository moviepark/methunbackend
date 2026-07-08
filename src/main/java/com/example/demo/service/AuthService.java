package com.example.demo.service;

import com.example.demo.dto.AuthRequestDto;
import com.example.demo.dto.AuthResponseDto;
import com.example.demo.dto.RegisterDto;
import com.example.demo.model.CampaignAccount;
import com.example.demo.repository.CampaignAccountRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {
    private static final Set<String> ROLES = Set.of(
            "CAMPAIGN_DIRECTOR",
            "FIELD_ORGANIZER",
            "OUTREACH_VOLUNTEER",
            "COMPLIANCE_AUDITOR"
    );

    private final CampaignAccountRepository campaignAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(CampaignAccountRepository campaignAccountRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.campaignAccountRepository = campaignAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponseDto register(RegisterDto dto) {
        validateRegister(dto);

        if (campaignAccountRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        String role = normalizeRole(dto.getDomainRole());

        CampaignAccount account = new CampaignAccount();
        account.setEmail(dto.getEmail().trim().toLowerCase());
        account.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        account.setFullName(dto.getFullName().trim());
        account.setDomainRole(role);
        account.setPhoneNumber(dto.getPhoneNumber());
        account.setActive(true);

        CampaignAccount saved = campaignAccountRepository.save(account);
        return buildResponse(saved);
    }

    public AuthResponseDto authenticate(AuthRequestDto dto) {
        if (dto.getEmail() == null || dto.getEmail().isBlank() || dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new BadCredentialsException("Invalid email or password");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail().trim().toLowerCase(), dto.getPassword())
        );

        CampaignAccount account = campaignAccountRepository.findByEmail(dto.getEmail().trim().toLowerCase())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!account.isActive()) {
            throw new BadCredentialsException("Account is inactive");
        }

        return buildResponse(account);
    }

    private AuthResponseDto buildResponse(CampaignAccount account) {
        String token = jwtService.generateToken(account.getEmail(), account.getDomainRole(), account.getAccountId());
        String refreshToken = jwtService.generateRefreshToken(account.getEmail());
        return new AuthResponseDto(token, refreshToken, account.getAccountId(), account.getEmail(), account.getFullName(), account.getDomainRole());
    }

    private void validateRegister(RegisterDto dto) {
        if (dto.getEmail() == null || dto.getEmail().isBlank()) throw new RuntimeException("Email is required");
        if (dto.getPassword() == null || dto.getPassword().isBlank()) throw new RuntimeException("Password is required");
        if (dto.getFullName() == null || dto.getFullName().isBlank()) throw new RuntimeException("Full name is required");
    }

    private String normalizeRole(String role) {
        if (role == null || role.isBlank()) return "OUTREACH_VOLUNTEER";
        String normalized = role.trim().toUpperCase();
        if (!ROLES.contains(normalized)) {
            throw new RuntimeException("Invalid domain role. Use CAMPAIGN_DIRECTOR, FIELD_ORGANIZER, OUTREACH_VOLUNTEER, or COMPLIANCE_AUDITOR");
        }
        return normalized;
    }
}
