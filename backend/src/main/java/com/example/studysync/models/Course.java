package com.example.studysync.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="course")
@Getter
@Setter
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonProperty("courseId")
    private Long id;

    @JsonProperty("courseCode")
    @Column(nullable=false, unique=true)
    private String code;

    @Column(nullable=false)
    private String name;

    private String department;

    private Integer credits;
    
}