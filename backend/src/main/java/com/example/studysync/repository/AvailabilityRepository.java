package com.example.studysync.repository;

import com.example.studysync.models.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findAvailabilityByStudent_Id(Long studentId);

}
