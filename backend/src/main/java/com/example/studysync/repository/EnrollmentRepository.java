package com.example.studysync.repository;

import com.example.studysync.models.Enrollment;
import com.example.studysync.models.id.EnrollmentId;
import org.springframework.data.repository.CrudRepository;

public interface EnrollmentRepository extends CrudRepository<Enrollment, EnrollmentId> {

}
