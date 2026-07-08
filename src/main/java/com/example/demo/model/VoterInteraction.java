package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "voter_interactions")
public class VoterInteraction {
    public enum InteractionChannel { CALL, SMS, IN_PERSON }
    public enum SentimentDetected { STRONG_SUPPORT, LEANING, UNDECIDED, OPPOSED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interaction_id")
    private Long interactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voter_id", nullable = false)
    private VoterProfile voter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id", nullable = false)
    private CampaignAccount volunteer;

    @Enumerated(EnumType.STRING)
    @Column(name = "interaction_channel", nullable = false, length = 40)
    private InteractionChannel interactionChannel;

    @Enumerated(EnumType.STRING)
    @Column(name = "sentiment_detected", nullable = false, length = 40)
    private SentimentDetected sentimentDetected;

    @Column(name = "policy_issue_tag", length = 100)
    private String policyIssueTag;

    @Column(name = "interaction_notes", length = 1000, columnDefinition = "TEXT")
    private String interactionNotes;

    @Column(name = "logged_at", nullable = false, updatable = false)
    private LocalDateTime loggedAt;

    @PrePersist
    public void prePersist() {
        if (loggedAt == null) loggedAt = LocalDateTime.now();
    }

    public Long getInteractionId() { return interactionId; }
    public void setInteractionId(Long interactionId) { this.interactionId = interactionId; }
    public VoterProfile getVoter() { return voter; }
    public void setVoter(VoterProfile voter) { this.voter = voter; }
    public CampaignAccount getVolunteer() { return volunteer; }
    public void setVolunteer(CampaignAccount volunteer) { this.volunteer = volunteer; }
    public InteractionChannel getInteractionChannel() { return interactionChannel; }
    public void setInteractionChannel(InteractionChannel interactionChannel) { this.interactionChannel = interactionChannel; }
    public SentimentDetected getSentimentDetected() { return sentimentDetected; }
    public void setSentimentDetected(SentimentDetected sentimentDetected) { this.sentimentDetected = sentimentDetected; }
    public String getPolicyIssueTag() { return policyIssueTag; }
    public void setPolicyIssueTag(String policyIssueTag) { this.policyIssueTag = policyIssueTag; }
    public String getInteractionNotes() { return interactionNotes; }
    public void setInteractionNotes(String interactionNotes) { this.interactionNotes = interactionNotes; }
    public LocalDateTime getLoggedAt() { return loggedAt; }
    public void setLoggedAt(LocalDateTime loggedAt) { this.loggedAt = loggedAt; }
}
