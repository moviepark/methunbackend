package com.example.demo.service;

import com.example.demo.dto.VolunteerShiftDto;
import com.example.demo.exception.ShiftLimitExceededException;
import com.example.demo.model.CampaignAccount;
import com.example.demo.model.OutreachEvent;
import com.example.demo.model.VolunteerShift;
import com.example.demo.repository.CampaignAccountRepository;
import com.example.demo.repository.OutreachEventRepository;
import com.example.demo.repository.VolunteerShiftRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VolunteerOperationsService {
    private final VolunteerShiftRepository volunteerShiftRepository;
    private final OutreachEventRepository outreachEventRepository;
    private final CampaignAccountRepository campaignAccountRepository;

    public VolunteerOperationsService(VolunteerShiftRepository volunteerShiftRepository,
                                      OutreachEventRepository outreachEventRepository,
                                      CampaignAccountRepository campaignAccountRepository) {
        this.volunteerShiftRepository = volunteerShiftRepository;
        this.outreachEventRepository = outreachEventRepository;
        this.campaignAccountRepository = campaignAccountRepository;
    }

    public List<VolunteerShiftDto> getAllShifts() {
        return volunteerShiftRepository.findAll().stream().map(this::toDto).toList();
    }

    public List<VolunteerShiftDto> getMySchedule(Long accountId) {
        return volunteerShiftRepository.findByAssignedVolunteerAccountId(accountId).stream().map(this::toDto).toList();
    }

    @Transactional
    public VolunteerShiftDto createShift(VolunteerShiftDto dto) {
        if (dto.getEventId() == null) throw new RuntimeException("Event ID is required");
        if (dto.getStartTime() == null || dto.getEndTime() == null) throw new RuntimeException("Shift start and end time are required");
        if (!dto.getEndTime().isAfter(dto.getStartTime())) throw new RuntimeException("Shift end time must be after start time");

        OutreachEvent event = outreachEventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new RuntimeException("OutreachEvent not found"));

        VolunteerShift shift = new VolunteerShift();
        shift.setEvent(event);
        shift.setStartTime(dto.getStartTime());
        shift.setEndTime(dto.getEndTime());
        shift.setShiftStatus(dto.getShiftStatus() == null ? VolunteerShift.ShiftStatus.OPEN : dto.getShiftStatus());
        return toDto(volunteerShiftRepository.save(shift));
    }

    @Transactional
    public VolunteerShiftDto reserveShiftSlot(Long shiftId, Long accountId) {
        VolunteerShift shift = volunteerShiftRepository.findByIdWithLock(shiftId)
                .orElseThrow(() -> new RuntimeException("VolunteerShift not found"));

        if (shift.getShiftStatus() != VolunteerShift.ShiftStatus.OPEN) {
            throw new IllegalStateException("Shift is not open for reservation");
        }

        CampaignAccount volunteer = campaignAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Volunteer account not found"));

        long active = volunteerShiftRepository.countByAssignedVolunteerAccountIdAndShiftStatusIn(
                accountId,
                List.of(VolunteerShift.ShiftStatus.RESERVED, VolunteerShift.ShiftStatus.OPEN)
        );

        if (active >= 5) {
            throw new ShiftLimitExceededException("Maximum of 5 active shift registrations allowed.");
        }

        shift.setAssignedVolunteer(volunteer);
        shift.setShiftStatus(VolunteerShift.ShiftStatus.RESERVED);

        OutreachEvent event = shift.getEvent();
        event.setCurrentRegistrations((event.getCurrentRegistrations() == null ? 0 : event.getCurrentRegistrations()) + 1);
        outreachEventRepository.save(event);

        return toDto(volunteerShiftRepository.save(shift));
    }

    @Transactional
    public VolunteerShiftDto updateStatus(Long id, VolunteerShift.ShiftStatus status) {
        VolunteerShift shift = volunteerShiftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VolunteerShift not found"));
        shift.setShiftStatus(status);
        return toDto(volunteerShiftRepository.save(shift));
    }

    @Transactional
    public void deleteShift(Long id) {
        volunteerShiftRepository.delete(volunteerShiftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VolunteerShift not found")));
    }

    public VolunteerShiftDto toDto(VolunteerShift shift) {
        VolunteerShiftDto dto = new VolunteerShiftDto();
        dto.setShiftId(shift.getShiftId());
        if (shift.getEvent() != null) {
            dto.setEventId(shift.getEvent().getEventId());
            dto.setEventTitle(shift.getEvent().getEventTitle());
        }
        if (shift.getAssignedVolunteer() != null) {
            dto.setAssignedVolunteerId(shift.getAssignedVolunteer().getAccountId());
            dto.setAssignedVolunteerName(shift.getAssignedVolunteer().getFullName());
        }
        dto.setStartTime(shift.getStartTime());
        dto.setEndTime(shift.getEndTime());
        dto.setShiftStatus(shift.getShiftStatus());
        return dto;
    }
}
