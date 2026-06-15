package com.example.studysync.controller;


import com.example.studysync.custom.CustomUserDetails;
import com.example.studysync.models.Course;
import com.example.studysync.models.Enrollment;
import com.example.studysync.models.id.EnrollmentId;
import com.example.studysync.repository.CourseRepository;
import com.example.studysync.repository.EnrollmentRepository;
import com.example.studysync.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("courses")
@AllArgsConstructor
public class CourseController {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;

    @GetMapping("/course-list")
    public List<Course> courseList(){
        return courseRepository.findAll();
    }

    @PostMapping("/course-list/add")
    public List<Course> courseListAdd(@RequestBody Course data){
        courseRepository.save(data);
        return courseRepository.findAll();
    }

    @GetMapping("/all")
    public ResponseEntity<?> myEnrolledCourses(@AuthenticationPrincipal CustomUserDetails currentUser){
        Long id = currentUser.getId();

        List<Course> allCourses = courseRepository.findAll();
        List<Course> enrolledCourses = courseRepository.findEnrolledCourses(id);

        return ResponseEntity.ok().body(
                Map.of(
                        "courseList", allCourses,
                        "enrolledCourses", enrolledCourses
                )
        );
    }

    @PostMapping("/enroll")
    public ResponseEntity<?> enroll(@RequestBody Course data, @AuthenticationPrincipal CustomUserDetails currentUser){
        Long id = currentUser.getId();
        String courseCode = data.getCode();
        Long courseId = data.getId();
        Optional<Course> courseExist = courseRepository.findCourseByStudentIdAndCourseCodeAndCourseId(id, courseCode, courseId);

        if (courseExist.isPresent()){
            return ResponseEntity.status(409).body("You are already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setId(new EnrollmentId(id, courseId));
        enrollment.setStudent(studentRepository.getReferenceById(id));
        enrollment.setCourse(courseRepository.getReferenceById(courseId));
        enrollmentRepository.save(enrollment);
        List<Course> enrolledCourses = courseRepository.findEnrolledCourses(id);

        return ResponseEntity.ok().body(enrolledCourses);
    }

    @DeleteMapping("/enroll/delete/{courseId}/{courseCode}")
    public List<Course> unEnroll(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable Long courseId, @PathVariable String courseCode){
        Long id = currentUser.getId();

        EnrollmentId enrolled = new EnrollmentId(id, courseId);
        enrollmentRepository.deleteById(enrolled);

        return courseRepository.findEnrolledCourses(id);
    }


}
