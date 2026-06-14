package com.example.studysync.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Entity
@Table(name= "availability")
@Getter
@Setter
public class Availability {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("day")
    private String dayOfWeek;

    @Column(nullable=false)
    private Instant startTime;

    @Column(nullable=false)
    private Instant endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id", nullable=false)
    private Student student;

}