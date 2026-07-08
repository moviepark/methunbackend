package com.example.demo.service;

import com.example.demo.dto.OutreachEventDto;
import com.example.demo.model.OutreachEvent;
import com.example.demo.repository.OutreachEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OutreachManagementService {
    private final OutreachEventRepository outreachEventRepository;

    public OutreachManagementService(OutreachEventRepository outreachEventRepository) {
        this.outreachEventRepository = outreachEventRepository;
    }

    public List<OutreachEventDto> getAllEvents() {
        return outreachEventRepository.findAll().stream().map(this::toDto).toList();
    }

    public OutreachEventDto getEventById(Long id) {
        return toDto(outreachEventRepository.findById(id).orElseThrow(() -> new RuntimeException("OutreachEvent not found")));
    }

    @Transactional
    public OutreachEventDto publishOutreachEvent(OutreachEventDto dto) {
        validateEvent(dto);
        if (dto.getScheduledDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Scheduled date must be in the future");
        }
        OutreachEvent event = new OutreachEvent();
        applyDto(event, dto);
        event.setEventStatus(OutreachEvent.EventStatus.PLANNED);
        event.setCurrentRegistrations(0);
        return toDto(outreachEventRepository.save(event));
    }

    @Transactional
    public OutreachEventDto updateEvent(Long id, OutreachEventDto dto) {
        OutreachEvent event = outreachEventRepository.findById(id).orElseThrow(() -> new RuntimeException("OutreachEvent not found"));
        if (event.getEventStatus() == OutreachEvent.EventStatus.COMPLETED || event.getEventStatus() == OutreachEvent.EventStatus.CANCELLED) {
            throw new IllegalStateException("Cannot modify finalized or cancelled outreach event");
        }
        applyDto(event, dto);
        if (dto.getCurrentRegistrations() != null) event.setCurrentRegistrations(dto.getCurrentRegistrations());
        if (dto.getEventStatus() != null) event.setEventStatus(dto.getEventStatus());
        return toDto(outreachEventRepository.save(event));
    }

    @Transactional
    public OutreachEventDto concludeEventWorkflows(Long id) {
        OutreachEvent event = outreachEventRepository.findById(id).orElseThrow(() -> new RuntimeException("OutreachEvent not found"));
        if (event.getEventStatus() == OutreachEvent.EventStatus.COMPLETED || event.getEventStatus() == OutreachEvent.EventStatus.CANCELLED) {
            throw new IllegalStateException("OutreachEvent already finalized or cancelled");
        }
        event.setEventStatus(OutreachEvent.EventStatus.COMPLETED);
        return toDto(outreachEventRepository.save(event));
    }

    @Transactional
    public void deleteEvent(Long id) {
        OutreachEvent event = outreachEventRepository.findById(id).orElseThrow(() -> new RuntimeException("OutreachEvent not found"));
        outreachEventRepository.delete(event);
    }

    private void validateEvent(OutreachEventDto dto) {
        if (dto.getEventTitle() == null || dto.getEventTitle().isBlank()) throw new RuntimeException("Event title is required");
        if (dto.getEventType() == null) throw new RuntimeException("Event type is required");
        if (dto.getLocation() == null || dto.getLocation().isBlank()) throw new RuntimeException("Location is required");
        if (dto.getScheduledDate() == null) throw new RuntimeException("Scheduled date is required");
        if (dto.getTargetCapacity() == null || dto.getTargetCapacity() <= 0) throw new RuntimeException("Target capacity must be greater than zero");
    }

    private void applyDto(OutreachEvent event, OutreachEventDto dto) {
        if (dto.getEventTitle() != null) event.setEventTitle(dto.getEventTitle());
        if (dto.getEventType() != null) event.setEventType(dto.getEventType());
        if (dto.getLocation() != null) event.setLocation(dto.getLocation());
        if (dto.getScheduledDate() != null) event.setScheduledDate(dto.getScheduledDate());
        if (dto.getTargetCapacity() != null) event.setTargetCapacity(dto.getTargetCapacity());
    }

    public OutreachEventDto toDto(OutreachEvent event) {
        OutreachEventDto dto = new OutreachEventDto();
        dto.setEventId(event.getEventId());
        dto.setEventTitle(event.getEventTitle());
        dto.setEventType(event.getEventType());
        dto.setLocation(event.getLocation());
        dto.setScheduledDate(event.getScheduledDate());
        dto.setTargetCapacity(event.getTargetCapacity());
        dto.setCurrentRegistrations(event.getCurrentRegistrations());
        dto.setEventStatus(event.getEventStatus());
        return dto;
    }
}
