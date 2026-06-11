package com.example.studysync.models;

import java.time.Instant;

import com.example.studysync.models.Student;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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