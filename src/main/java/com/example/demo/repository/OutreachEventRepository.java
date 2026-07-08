package com.example.demo.repository;

import com.example.demo.model.OutreachEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface OutreachEventRepository extends JpaRepository<OutreachEvent, Long> {
    List<OutreachEvent> findByEventStatus(OutreachEvent.EventStatus eventStatus);

    @Query("SELECT e FROM OutreachEvent e WHERE e.scheduledDate >= :afterDate ORDER BY e.scheduledDate ASC")
    List<OutreachEvent> findUpcomingEvents(@Param("afterDate") LocalDateTime afterDate);
}
