package com.example.studysync.controller;


import com.example.studysync.custom.CustomUserDetails;
import com.example.studysync.dto.StudentDTO;
import com.example.studysync.models.Availability;
import com.example.studysync.repository.AvailabilityRepository;
import com.example.studysync.repository.StudentRepository;
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

    @PostMapping("/availability/add")
    public List<Availability> addAvailability(@RequestBody Availability data, @AuthenticationPrincipal CustomUserDetails currentUser){
        Long id = currentUser.getId();
        availabilityRepository.save(data);
        return availabilityRepository.findAvailabilityByStudent_Id(id);

    }
}
