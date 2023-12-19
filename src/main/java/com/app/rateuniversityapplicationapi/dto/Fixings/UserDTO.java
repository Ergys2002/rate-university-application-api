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
public class UserDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    Set<CourseDTO> courses = new HashSet<>();

    public UserDTO(){};

    public UserDTO(User user){
        this.id = user.getId();
        this.firstName = user.getFirstname();
        this.lastName =user.getLastname();
        this.email = user.getEmail();

        for (Course course:
                user.getEnrolledCourses()) {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setId(course.getId());
            courseDTO.setTitle(course.getTitle());
            courses.add(courseDTO);
        }
    }
}
