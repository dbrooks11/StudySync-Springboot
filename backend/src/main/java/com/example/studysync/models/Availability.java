package com.example.studysync.models;

import java.time.Instant;

import com.example.studysync.models.Student;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;




@Entity
@Table(name= "availability")
@Getter
@Setter
public class Availability {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String dayOfWeek;

    @Column(nullable=false)
    private Instant startTime;

    @Column(nullable=false)
    private Instant endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id", nullable=false)
    private Student student;

}