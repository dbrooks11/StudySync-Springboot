package com.example.studysync.repository;

import com.example.studysync.models.Participating;
import com.example.studysync.models.id.ParticipatingId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParticipatingRepository extends JpaRepository<Participating, ParticipatingId> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Participating WHERE group.id = :groupId")
    void deleteAllByGroupId(@Param("groupId") Long groupId);

    Integer countByGroup_Id(Long groupId);
}
