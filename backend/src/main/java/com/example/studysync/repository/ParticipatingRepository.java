package com.example.studysync.repository;

import com.example.studysync.models.Participating;
import com.example.studysync.models.id.ParticipatingId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipatingRepository extends JpaRepository<Participating, ParticipatingId> {

}
