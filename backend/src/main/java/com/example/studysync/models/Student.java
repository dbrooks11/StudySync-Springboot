package com.example.studysync.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;


@Entity
@Table(name= "student")
@Setter
@Getter
@NoArgsConstructor
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String firstName;

    @Column(nullable=false)
    private String lastName;
    
    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    @JsonProperty("password")
    private String passwordHash;

    private String major;

    @Column(precision = 2)
    private Double gpa;

    @CreationTimestamp
    @Column(updatable=false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @OneToMany(mappedBy="student", cascade=CascadeType.ALL)
    private List<Availability> availabilities;

}