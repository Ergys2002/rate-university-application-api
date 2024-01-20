package com.app.rateuniversityapplicationapi.entity;

import com.app.rateuniversityapplicationapi.entity.User;
import com.app.rateuniversityapplicationapi.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testSaveAndRetrieveUser() {
        User user = User.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john@example.com")
                .password("password")
                .birthDate(LocalDate.of(1990, 1, 1))
                .phoneNumber("123456789")
                .role(Role.USER)
                .build();
        user = entityManager.persistAndFlush(user);

        User foundUser = entityManager.find(User.class, user.getId());
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("john@example.com");
    }

    // Add more tests for update, delete, UserDetails methods, etc.
}
