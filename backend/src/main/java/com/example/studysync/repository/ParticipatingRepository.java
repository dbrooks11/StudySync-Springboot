package com.example.studysync.repository;

import com.example.studysync.models.Participating;
import com.example.studysync.models.id.ParticipatingId;
import org.springframework.data.repository.CrudRepository;

public interface ParticipatingRepository extends CrudRepository<Participating, ParticipatingId> {

}
