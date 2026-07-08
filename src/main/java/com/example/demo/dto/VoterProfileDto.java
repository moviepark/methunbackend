package com.example.demo.dto;

import com.example.demo.model.VoterProfile;
import java.time.LocalDateTime;

public class VoterProfileDto {
    private Long voterId;
    private String firstName;
    private String lastName;
    private String electoralDistrict;
    private String contactNumber;
    private Integer supportScore;
    private VoterProfile.EngagementStatus engagementStatus;
    private LocalDateTime lastContactedDate;

    public Long getVoterId() { return voterId; }
    public void setVoterId(Long voterId) { this.voterId = voterId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getElectoralDistrict() { return electoralDistrict; }
    public void setElectoralDistrict(String electoralDistrict) { this.electoralDistrict = electoralDistrict; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public Integer getSupportScore() { return supportScore; }
    public void setSupportScore(Integer supportScore) { this.supportScore = supportScore; }
    public VoterProfile.EngagementStatus getEngagementStatus() { return engagementStatus; }
    public void setEngagementStatus(VoterProfile.EngagementStatus engagementStatus) { this.engagementStatus = engagementStatus; }
    public LocalDateTime getLastContactedDate() { return lastContactedDate; }
    public void setLastContactedDate(LocalDateTime lastContactedDate) { this.lastContactedDate = lastContactedDate; }
}
