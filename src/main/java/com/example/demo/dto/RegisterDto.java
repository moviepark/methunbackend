package com.example.demo.dto;

public class RegisterDto {
    private String email;
    private String password;
    private String fullName;
    private String domainRole;
    private String phoneNumber;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getDomainRole() { return domainRole; }
    public void setDomainRole(String domainRole) { this.domainRole = domainRole; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
