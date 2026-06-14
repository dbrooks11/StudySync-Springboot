package com.example.studysync.dto;

import com.example.studysync.models.Availability;

import java.util.List;

public interface StudentDTO {
    String getEmail();
    String getFirstName();
    String getLastName();
    String getGpa();
    String getMajor();
    List<Availability> getAvailabilities();
}
