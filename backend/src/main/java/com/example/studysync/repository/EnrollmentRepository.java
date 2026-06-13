package com.example.studysync.repository;

import com.example.studysync.models.Enrollment;
import com.example.studysync.models.id.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {

}
