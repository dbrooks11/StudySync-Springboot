package com.example.studysync.repository;

import com.example.studysync.dto.StudentDTO;
import com.example.studysync.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
    Optional<StudentDTO> findStudentById(Long id);
}
