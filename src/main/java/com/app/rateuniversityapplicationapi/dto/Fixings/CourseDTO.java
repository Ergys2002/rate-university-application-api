package com.app.rateuniversityapplicationapi.dto.Fixings;

import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
public class CourseDTO {

    private UUID id;
    private String title;
    private Set<UserDTO> enrolledUsers = new HashSet<>();

    public CourseDTO(){};

    public CourseDTO(Course course){
        this.id = course.getId();
        for (User enrolledStudent:course.getRegisteredStudents()) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(enrolledStudent.getId());
            userDTO.setEmail(enrolledStudent.getEmail());
            userDTO.setFirstName(enrolledStudent.getFirstname());
            userDTO.setLastName(enrolledStudent.getLastname());
            enrolledUsers.add(userDTO);
        }
    }


}
