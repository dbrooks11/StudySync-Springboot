package com.example.studysync.controller;


import com.example.studysync.custom.CustomUserDetails;
import com.example.studysync.dto.StudyGroupDTO;
import com.example.studysync.models.Course;
import com.example.studysync.models.Participating;
import com.example.studysync.models.Student;
import com.example.studysync.models.StudyGroup;
import com.example.studysync.models.id.ParticipatingId;
import com.example.studysync.repository.CourseRepository;
import com.example.studysync.repository.ParticipatingRepository;
import com.example.studysync.repository.StudentRepository;
import com.example.studysync.repository.StudyGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/groups")
@AllArgsConstructor
public class GroupController {

    private final StudyGroupRepository studyGroupRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ParticipatingRepository participatingRepository;

    @PostMapping("/create/{courseId}")
    public ResponseEntity<?> createGroup(@RequestBody StudyGroup data,
                                         @PathVariable Long courseId,
                                         @AuthenticationPrincipal CustomUserDetails currentUser){
        Long id = currentUser.getId();

        if (data.getMaxSize() <= 0){
            return ResponseEntity.status(400).body("Maximum Group size must be greater than 0");
        }

        Student student = studentRepository.getReferenceById(id);
        Course course = courseRepository.getReferenceById(courseId);

        data.setHost(student);
        data.setCourse(course);
        studyGroupRepository.save(data);

        return ResponseEntity.ok().body("Study group successfully created");
    }

    @GetMapping("/me")
    public ResponseEntity<?> myCreatedGroups(@AuthenticationPrincipal CustomUserDetails currentUser){
        Long id = currentUser.getId();

        List<StudyGroup> myGroups = studyGroupRepository.findByHost_Id(id);

        List<StudyGroupDTO> joinedGroups = studyGroupRepository.findJoinedGroupsByStudentId(id);

        return ResponseEntity.ok().body(
                Map.of(
                        "myGroups", myGroups,
                        "joinedGroups", joinedGroups
                )
        );
    }

    @DeleteMapping("/delete/{groupId}/{courseId}")
    public List<StudyGroup> deleteGroup(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable Long groupId, @PathVariable Long courseId){
        Long id = currentUser.getId();

        participatingRepository.deleteAllByGroupId(groupId);
        studyGroupRepository.deleteStudyGroupByIdAndHost_IdAndCourse_Id(groupId,id,courseId);

        return studyGroupRepository.findByHost_Id(id);
    }

    @DeleteMapping("/leave/{groupId}")
    public List<StudyGroupDTO> leaveGroup(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable Long groupId){
        Long id = currentUser.getId();

        participatingRepository.deleteById(new ParticipatingId(id, groupId));

        return studyGroupRepository.findJoinedGroupsByStudentId(id);
    }

    @GetMapping("/all")
    public List<StudyGroupDTO> allGroups(@AuthenticationPrincipal CustomUserDetails currentUser){
        Long id = currentUser.getId();
        return studyGroupRepository.findAllGroups(id);
    }

    @GetMapping("/all/recommend")
    public List<StudyGroupDTO> recommendedGroups(@AuthenticationPrincipal CustomUserDetails currentUser){
        Long id = currentUser.getId();

        return studyGroupRepository.findAllRecommendedGroups(id);
    }

    @PostMapping("/join/{groupId}")
    public ResponseEntity<?> joinGroup(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable Long groupId){
        Long id = currentUser.getId();

        Optional<StudyGroup> group = studyGroupRepository.findById(groupId);
        Integer count = participatingRepository.countByGroup_Id(groupId);
        if (group.isEmpty() || count >= group.get().getMaxSize()){
            return ResponseEntity.status(404).body("Study group does not exist or is full");
        }

        Student student = studentRepository.getReferenceById(id);
        Participating join = new Participating();
        join.setGroup(group.get());
        join.setStudent(student);
        join.setId(new ParticipatingId(id, groupId));
        participatingRepository.save(join);

        return ResponseEntity.ok().body("Joined group successfully");
    }
}
