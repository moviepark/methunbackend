package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "volunteer_shifts")
public class VolunteerShift {
    public enum ShiftStatus { OPEN, RESERVED, COMPLETED, MISSED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shift_id")
    private Long shiftId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private OutreachEvent event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_volunteer")
    private CampaignAccount assignedVolunteer;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "shift_status", nullable = false, length = 40)
    private ShiftStatus shiftStatus = ShiftStatus.OPEN;

    @PrePersist
    public void prePersist() {
        if (shiftStatus == null) shiftStatus = ShiftStatus.OPEN;
    }

    public Long getShiftId() { return shiftId; }
    public void setShiftId(Long shiftId) { this.shiftId = shiftId; }
    public OutreachEvent getEvent() { return event; }
    public void setEvent(OutreachEvent event) { this.event = event; }
    public CampaignAccount getAssignedVolunteer() { return assignedVolunteer; }
    public void setAssignedVolunteer(CampaignAccount assignedVolunteer) { this.assignedVolunteer = assignedVolunteer; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public ShiftStatus getShiftStatus() { return shiftStatus; }
    public void setShiftStatus(ShiftStatus shiftStatus) { this.shiftStatus = shiftStatus; }
}
