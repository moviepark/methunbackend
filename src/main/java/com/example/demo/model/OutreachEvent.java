package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "outreach_events")
public class OutreachEvent {
    public enum EventType { RALLY, PHONE_BANK, TOWN_HALL, DOOR_TO_DOOR }
    public enum EventStatus { PLANNED, ACTIVE, COMPLETED, CANCELLED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "event_title", nullable = false, length = 150)
    private String eventTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 40)
    private EventType eventType;

    @Column(nullable = false, length = 200)
    private String location;

    @Column(name = "scheduled_date", nullable = false)
    private LocalDateTime scheduledDate;

    @Column(name = "target_capacity", nullable = false)
    private Integer targetCapacity;

    @Column(name = "current_registrations", nullable = false)
    private Integer currentRegistrations = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_status", nullable = false, length = 40)
    private EventStatus eventStatus = EventStatus.PLANNED;

    @PrePersist
    public void prePersist() {
        if (currentRegistrations == null) currentRegistrations = 0;
        if (eventStatus == null) eventStatus = EventStatus.PLANNED;
    }

    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
    public String getEventTitle() { return eventTitle; }
    public void setEventTitle(String eventTitle) { this.eventTitle = eventTitle; }
    public EventType getEventType() { return eventType; }
    public void setEventType(EventType eventType) { this.eventType = eventType; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public LocalDateTime getScheduledDate() { return scheduledDate; }
    public void setScheduledDate(LocalDateTime scheduledDate) { this.scheduledDate = scheduledDate; }
    public Integer getTargetCapacity() { return targetCapacity; }
    public void setTargetCapacity(Integer targetCapacity) { this.targetCapacity = targetCapacity; }
    public Integer getCurrentRegistrations() { return currentRegistrations; }
    public void setCurrentRegistrations(Integer currentRegistrations) { this.currentRegistrations = currentRegistrations; }
    public EventStatus getEventStatus() { return eventStatus; }
    public void setEventStatus(EventStatus eventStatus) { this.eventStatus = eventStatus; }
}
