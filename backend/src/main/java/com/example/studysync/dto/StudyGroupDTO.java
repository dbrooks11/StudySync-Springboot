package com.example.studysync.dto;

import java.time.Instant;

public interface StudyGroupDTO {
    Long getId();
    Long getCourseId();
    String getName();
    String getLocation();
    Instant getMeetingTime();
    Integer getMaxSize();

    String getCourseName();
    String getCourseCode();
    Integer getSpotsLeft();
    Boolean getIsJoined();
}
