package com.example.studysync.repository;

import com.example.studysync.models.Availability;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findAvailabilityByStudent_Id(Long studentId);

    Optional<Availability> findAvailabilityByStudent_IdAndDayOfWeek(Long studentId, String dayOfWeek);

    @Transactional
    void deleteAvailabilityByIdAndDayOfWeekAndStudent_Id(Long id, String dayOfWeek, Long studentId);
}
