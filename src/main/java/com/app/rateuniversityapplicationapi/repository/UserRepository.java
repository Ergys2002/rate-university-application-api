package com.app.rateuniversityapplicationapi.repository;

import com.app.rateuniversityapplicationapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User,UUID> {
    User findByEmail(String email);
    @Query("select count(u.id) from User u")
    int getNumberOfStudents();
}
