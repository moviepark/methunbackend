package com.example.demo.dto;

import com.example.demo.model.VolunteerShift;
import java.time.LocalDateTime;

public class VolunteerShiftDto {
    private Long shiftId;
    private Long eventId;
    private String eventTitle;
    private Long assignedVolunteerId;
    private String assignedVolunteerName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private VolunteerShift.ShiftStatus shiftStatus;

    public Long getShiftId() { return shiftId; }
    public void setShiftId(Long shiftId) { this.shiftId = shiftId; }
    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
    public String getEventTitle() { return eventTitle; }
    public void setEventTitle(String eventTitle) { this.eventTitle = eventTitle; }
    public Long getAssignedVolunteerId() { return assignedVolunteerId; }
    public void setAssignedVolunteerId(Long assignedVolunteerId) { this.assignedVolunteerId = assignedVolunteerId; }
    public String getAssignedVolunteerName() { return assignedVolunteerName; }
    public void setAssignedVolunteerName(String assignedVolunteerName) { this.assignedVolunteerName = assignedVolunteerName; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public VolunteerShift.ShiftStatus getShiftStatus() { return shiftStatus; }
    public void setShiftStatus(VolunteerShift.ShiftStatus shiftStatus) { this.shiftStatus = shiftStatus; }
}
