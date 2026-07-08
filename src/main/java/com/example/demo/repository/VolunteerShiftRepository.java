package com.example.demo.repository;

import com.example.demo.model.VolunteerShift;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface VolunteerShiftRepository extends JpaRepository<VolunteerShift, Long> {
    List<VolunteerShift> findByEventEventId(Long eventId);
    List<VolunteerShift> findByAssignedVolunteerAccountId(Long accountId);
    long countByAssignedVolunteerAccountIdAndShiftStatusIn(Long accountId, Collection<VolunteerShift.ShiftStatus> statuses);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM VolunteerShift s WHERE s.shiftId = :shiftId")
    Optional<VolunteerShift> findByIdWithLock(@Param("shiftId") Long shiftId);
}
