package com.example.studysync.repository;

import com.example.studysync.dto.StudyGroupDTO;
import com.example.studysync.models.StudyGroup;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {
    List<StudyGroup> findByHost_Id(Long hostId);

    @Transactional
    void deleteStudyGroupByIdAndHost_IdAndCourse_Id(Long id, Long hostId, Long courseId);

    @Query(value = """
        SELECT sg.id,
           sg.course_id AS courseId,
           sg.name,
           sg.location,
           sg.meeting_time,
           sg.max_size,
           c.name AS courseName,
           c.code AS courseCode,
           (sg.max_size - COUNT(p.student_id)) AS spotsLeft
         FROM study_group as sg
         JOIN participating as p ON p.group_id = sg.id
         JOIN course as c ON c.id = sg.course_id
         WHERE p.student_id = :studentId
         GROUP BY
            sg.id,
            sg.course_id,
            sg.name,
            sg.location,
            sg.meeting_time,
            sg.max_size,
            c.name,
            c.code
""", nativeQuery = true)
    List<StudyGroupDTO> findJoinedGroupsByStudentId(@Param("studentId") Long studentId);

    @Query(value = """
        SELECT
            sg.id,
            sg.course_id as courseId,
            sg.name,
            sg.location,
            sg.meeting_time,
            sg.max_size,
            c.name as courseName,
            c.code as courseCode,
            (sg.max_size - COUNT(p.student_id)) AS spotsLeft,
            BOOL_OR(p.student_id = :studentId) AS isJoined
        FROM study_group as sg
        JOIN course as c ON c.id = sg.course_id
        LEFT JOIN participating as p ON sg.id = p.group_id
        WHERE sg.host_id != :studentId
        AND sg.id NOT IN (
            SELECT group_id FROM participating WHERE student_id = :studentId
        )
        GROUP BY 
            sg.id,
            sg.name,
            sg.location,
            sg.meeting_time,
            sg.max_size,
            sg.course_id,
            c.name,
            c.code
        HAVING COUNT(p.student_id) < sg.max_size
        ORDER BY sg.created_at DESC
""", nativeQuery = true)
    List<StudyGroupDTO> findAllGroups(@Param("studentId") Long studentId);


    @Query(value = """
        SELECT
            sg.id,
            sg.course_id as courseId,
            sg.name,
            sg.location,
            sg.meeting_time,
            sg.max_size,
            c.name as courseName,
            c.code as courseCode,
            (sg.max_size - COUNT(p.student_id)) AS spotsLeft,
            BOOL_OR(p.student_id = :studentId) AS isJoined
        FROM study_group as sg
        JOIN enrollment as e ON e.course_id = sg.course_id
        JOIN course as c ON c.id = sg.course_id
        JOIN availability as a ON e.student_id = a.student_id
        LEFT JOIN participating as p ON sg.id = p.group_id
        WHERE e.student_id = :studentId
        AND sg.host_id != :studentId
        AND sg.id NOT IN (
            SELECT group_id FROM participating WHERE student_id = :studentId
        )
        AND a.day_of_week = TRIM(TO_CHAR(sg.meeting_time, 'Day'))
        AND sg.meeting_time::time >= a.start_time
        AND sg.meeting_time::time <= a.end_time
        GROUP BY 
            sg.id,
            sg.name,
            sg.location,
            sg.meeting_time,
            sg.max_size,
            sg.course_id,
            c.name,
            c.code
        HAVING COUNT(p.student_id) < sg.max_size
        ORDER BY sg.created_at DESC
""", nativeQuery = true)
    List<StudyGroupDTO> findAllRecommendedGroups(@Param("studentId") Long studentId);
}
