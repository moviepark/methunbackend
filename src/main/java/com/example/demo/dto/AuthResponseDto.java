package com.example.demo.dto;

public class AuthResponseDto {
    private String token;
    private String refreshToken;
    private Long accountId;
    private String email;
    private String fullName;
    private String domainRole;

    public AuthResponseDto() {}

    public AuthResponseDto(String token, String refreshToken, Long accountId, String email, String fullName, String domainRole) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.accountId = accountId;
        this.email = email;
        this.fullName = fullName;
        this.domainRole = domainRole;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getDomainRole() { return domainRole; }
    public void setDomainRole(String domainRole) { this.domainRole = domainRole; }
}
