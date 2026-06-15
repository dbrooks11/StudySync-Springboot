package com.example.studysync.repository;

import com.example.studysync.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query(value = """
        SELECT c.*
        FROM Course as c
        JOIN Enrollment as e ON e.course_id = c.id
        JOIN Student as s ON s.id = e.student_id
        WHERE s.id = :studentId
        """
    , nativeQuery = true)
    List<Course> findEnrolledCourses(@Param("studentId") Long studentId);

    @Query(value = """
      SELECT c.*
      FROM course as c
      JOIN Enrollment as e ON e.course_id = c.id
      JOIN Student as s ON s.id = e.student_id
      WHERE s.id = :studentId AND c.code = :courseCode AND c.id = :courseId
    """, nativeQuery = true)
    Optional<Course> findCourseByStudentIdAndCourseCodeAndCourseId(
            @Param("studentId") Long studentId,
            @Param("courseCode") String code,
            @Param("courseId") Long id
    );

}
