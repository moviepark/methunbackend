package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
@Table(name = "voter_profiles")
public class VoterProfile {
    public enum EngagementStatus { UNCONTACTED, CONTACTED, FOLLOW_UP_REQUIRED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voter_id")
    private Long voterId;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "electoral_district", nullable = false, length = 100)
    private String electoralDistrict;

    @Column(name = "contact_number", length = 30)
    private String contactNumber;

    @Min(0)
    @Max(100)
    @Column(name = "support_score", nullable = false)
    private Integer supportScore = 50;

    @Enumerated(EnumType.STRING)
    @Column(name = "engagement_status", nullable = false, length = 40)
    private EngagementStatus engagementStatus = EngagementStatus.UNCONTACTED;

    @Column(name = "last_contacted_date")
    private LocalDateTime lastContactedDate;

    @PrePersist
    public void prePersist() {
        if (supportScore == null) supportScore = 50;
        if (engagementStatus == null) engagementStatus = EngagementStatus.UNCONTACTED;
    }

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
    public EngagementStatus getEngagementStatus() { return engagementStatus; }
    public void setEngagementStatus(EngagementStatus engagementStatus) { this.engagementStatus = engagementStatus; }
    public LocalDateTime getLastContactedDate() { return lastContactedDate; }
    public void setLastContactedDate(LocalDateTime lastContactedDate) { this.lastContactedDate = lastContactedDate; }
}
