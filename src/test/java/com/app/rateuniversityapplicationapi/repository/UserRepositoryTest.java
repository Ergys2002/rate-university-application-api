package com.app.rateuniversityapplicationapi.repository;

import com.app.rateuniversityapplicationapi.entity.User;
import com.app.rateuniversityapplicationapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByEmail() {
        // Given
        User user = new User();
        user.setEmail("test@example.com");
        user.setFirstname("John");
        user.setLastname("Doe");
        // ... set other properties as needed

        userRepository.save(user);

        // When
        User foundUser = userRepository.findByEmail("test@example.com");

        // Then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("test@example.com");
        // ... other assertions as needed
    }

    @Test
    public void testH2DatabaseConnection() {
        try (
                Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "")) {
            assertTrue(connection.isValid(1));
        } catch (SQLException e) {
            fail("Should have connected to H2 database");
        }
    }

    // Similar tests for other methods...
}
