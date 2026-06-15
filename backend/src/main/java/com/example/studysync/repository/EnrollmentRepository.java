package com.example.studysync.repository;

import com.example.studysync.models.Enrollment;
import com.example.studysync.models.id.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {

    Optional<Enrollment> findEnrollmentByStudent_IdAndCourse_Id(Long studentId, Long courseId);
}
