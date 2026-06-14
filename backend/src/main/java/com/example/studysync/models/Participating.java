package com.example.studysync.models;

import com.example.studysync.models.id.ParticipatingId;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "participating")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Participating {

    @EmbeddedId
    private ParticipatingId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentId")
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("groupId")
    @JoinColumn(name = "course_id", nullable = false)
    private StudyGroup group;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant joinedAt;
}