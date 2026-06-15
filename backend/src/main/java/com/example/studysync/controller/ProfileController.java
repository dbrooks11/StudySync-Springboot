package com.example.studysync.controller;


import com.example.studysync.custom.CustomUserDetails;
import com.example.studysync.dto.StudentDTO;
import com.example.studysync.models.Availability;
import com.example.studysync.models.Student;
import com.example.studysync.repository.AvailabilityRepository;
import com.example.studysync.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final StudentRepository studentRepository;
    private final AvailabilityRepository availabilityRepository;

    ProfileController(StudentRepository studentRepository, AvailabilityRepository availabilityRepository){

        this.studentRepository = studentRepository;
        this.availabilityRepository = availabilityRepository;
    }

    @GetMapping("/me")
    public Optional<StudentDTO> profile(@AuthenticationPrincipal CustomUserDetails currentUser) {
        Long id = currentUser.getId();
        return studentRepository.findStudentById(id);
    }

    @PatchMapping("/edit")
    public Optional<StudentDTO> profileEdit(@RequestBody Student updateData, @AuthenticationPrincipal CustomUserDetails currentUser){
        Long id = currentUser.getId();

        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (updateData.getGpa() != null) {
            existingStudent.setGpa(updateData.getGpa());
        }
        if (updateData.getMajor() != null) {
            existingStudent.setMajor(updateData.getMajor());
        }
        if (updateData.getEmail() != null) {
            existingStudent.setEmail(updateData.getEmail());
        }
        studentRepository.save(existingStudent);
        return studentRepository.findStudentById(id);
    }

    @PostMapping("/availability/add")
    public ResponseEntity<?> addAvailability(@RequestBody Availability data, @AuthenticationPrincipal CustomUserDetails currentUser){
        Long id = currentUser.getId();
        Student student = studentRepository.getReferenceById(id);

        Optional<Availability> exist = availabilityRepository.findAvailabilityByStudent_IdAndDayOfWeek(id, data.getDayOfWeek());

        if (exist.isPresent()) {
            return ResponseEntity.status(409).body("Availability already exist");
        }

        data.setStudent(student);
        availabilityRepository.save(data);
        return ResponseEntity.ok().body(availabilityRepository.findAvailabilityByStudent_Id(student.getId()));
    }

    @DeleteMapping("/availability/delete/{availId}/{day}")
    public List<Availability> deleteAvailability(@PathVariable Long availId, @PathVariable String day, @AuthenticationPrincipal CustomUserDetails currentUser){
        Long id = currentUser.getId();

        availabilityRepository.deleteAvailabilityByIdAndDayOfWeekAndStudent_Id(availId, day, id);
        return availabilityRepository.findAvailabilityByStudent_Id(id);
    }
}
