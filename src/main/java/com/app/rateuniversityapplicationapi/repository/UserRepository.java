package com.app.rateuniversityapplicationapi.repository;

import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User,UUID> {

    List<User> getUsersByEnrolledCoursesContains(Course course);

    User findByEmail(String email);
    @Query("select count(u.id) from User u")
    int getNumberOfStudents();

    //Needed for CourseRepository
    User findUserById(UUID uuid);
    @Query("from User u where u.email = :email")

    UserDetails findByLogin(@Param("email") String email);
}
