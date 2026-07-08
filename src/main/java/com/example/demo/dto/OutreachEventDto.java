package com.example.demo.dto;

import com.example.demo.model.OutreachEvent;
import java.time.LocalDateTime;

public class OutreachEventDto {
    private Long eventId;
    private String eventTitle;
    private OutreachEvent.EventType eventType;
    private String location;
    private LocalDateTime scheduledDate;
    private Integer targetCapacity;
    private Integer currentRegistrations;
    private OutreachEvent.EventStatus eventStatus;

    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
    public String getEventTitle() { return eventTitle; }
    public void setEventTitle(String eventTitle) { this.eventTitle = eventTitle; }
    public OutreachEvent.EventType getEventType() { return eventType; }
    public void setEventType(OutreachEvent.EventType eventType) { this.eventType = eventType; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public LocalDateTime getScheduledDate() { return scheduledDate; }
    public void setScheduledDate(LocalDateTime scheduledDate) { this.scheduledDate = scheduledDate; }
    public Integer getTargetCapacity() { return targetCapacity; }
    public void setTargetCapacity(Integer targetCapacity) { this.targetCapacity = targetCapacity; }
    public Integer getCurrentRegistrations() { return currentRegistrations; }
    public void setCurrentRegistrations(Integer currentRegistrations) { this.currentRegistrations = currentRegistrations; }
    public OutreachEvent.EventStatus getEventStatus() { return eventStatus; }
    public void setEventStatus(OutreachEvent.EventStatus eventStatus) { this.eventStatus = eventStatus; }
}
