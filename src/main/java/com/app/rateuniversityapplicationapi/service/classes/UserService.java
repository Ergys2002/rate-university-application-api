package com.app.rateuniversityapplicationapi.service.classes;

import com.app.rateuniversityapplicationapi.config.JwtService;
import com.app.rateuniversityapplicationapi.dto.requests.AuthenticationRequest;
import com.app.rateuniversityapplicationapi.dto.requests.RegisterRequest;
import com.app.rateuniversityapplicationapi.dto.requests.UpdateUserRequest;
import com.app.rateuniversityapplicationapi.dto.responses.AuthenticationResponse;
import com.app.rateuniversityapplicationapi.dto.responses.UserResponse;
import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.entity.Role;
import com.app.rateuniversityapplicationapi.entity.User;
import com.app.rateuniversityapplicationapi.repository.CourseRepository;
import com.app.rateuniversityapplicationapi.repository.UserRepository;
import com.app.rateuniversityapplicationapi.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final CourseRepository courseRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<AuthenticationResponse> register(RegisterRequest request) {

        User fromDb = userRepository.findByEmail(request.getEmail());


        if (fromDb == null) {
            var user = User.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .birthDate(request.getBirthDate())
                    .phoneNumber(request.getPhoneNumber())
                    .role(Role.USER)
                    .build();

            userRepository.save(user);
            String jwtToken = jwtService.generateToken(user);
            return new ResponseEntity<>(AuthenticationResponse.builder()
                    .token(jwtToken)
                    .message("Registered Succesfully")
                    .validUntil(
                            jwtService.extractAllClaims(jwtToken)
                                    .getExpiration()
                                    .toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate())
                    .build(), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public int getNumberOfStudents() {
        return userRepository.getNumberOfStudents();
    }

    @Override
    public User findUserById(UUID uuid) {
        return userRepository.findUserById(uuid);
    }


    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail());

        String jwtToken = jwtService.generateToken(user);

        return new ResponseEntity<>(AuthenticationResponse.builder()
                .token(jwtToken)
                .message("Logged in Succesfully")
                .validUntil(
                        jwtService.extractAllClaims(jwtToken)
                                .getExpiration()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate())
                .build(), HttpStatus.ACCEPTED);
    }


    public UserResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null; // No user authenticated
        }

        User userFromDb = userRepository.findByEmail(authentication.getName());
        return new UserResponse() {
            @Override
            public String getFirstName() {
                return userFromDb.getFirstname();
            }

            @Override
            public String getLastName() {
                return userFromDb.getLastname();
            }

            @Override
            public String getEmail() {
                return userFromDb.getEmail();
            }

            @Override
            public LocalDate getBirthDate() {
                return userFromDb.getBirthDate();
            }

            @Override
            public String getPhoneNumber() {
                return userFromDb.getPhoneNumber();
            }

            @Override
            public String getProfilePhotoUrl() {
                return userFromDb.getProfilePhotoURL();
            }
        };
    }

    @Transactional
    @Override
    public void dropCourse(UUID uuid, String email) {
        Course course = courseRepository.getCourseById(uuid);
        Set<User> enrolledUsers = course.getRegisteredStudents();
        System.out.println(enrolledUsers.size());
        for (User student : enrolledUsers) {
            if (student.getEmail().equals(email)) {
                System.out.println(student.getEmail());
                enrolledUsers.remove(student);
                course.setEnrolledStudents(enrolledUsers.size());
                System.out.println(enrolledUsers.size());
                break;
            }
        }
        System.out.println("ENROLLED USERS: " + enrolledUsers);
        course.setRegisteredStudents(enrolledUsers);
        System.out.println("AFTERR" + course.getRegisteredStudents().size());
        courseRepository.save(course);
    }

    @Override
    public ResponseEntity<AuthenticationResponse> updateUser(UpdateUserRequest request) {
        System.out.println(request);
        String email = getCurrentUser().getEmail();
        User userToBeUpdated = userRepository.findByEmail(email);

        userToBeUpdated.setFirstname(request.getFirstname());
        userToBeUpdated.setLastname(request.getLastname());
        userToBeUpdated.setPhoneNumber(request.getPhoneNumber());
        if (request.getProfilePhoto() != null) {
            String newProfilePhotoPath = saveProfileImage(userToBeUpdated, request.getProfilePhoto());
            userToBeUpdated.setProfilePhotoURL(newProfilePhotoPath);
        }
        if (Objects.equals(request.getPassword(), "")) {
            userToBeUpdated.setPassword(userToBeUpdated.getPassword());
        } else {
            userToBeUpdated.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(userToBeUpdated);

        if (Objects.equals(request.getPassword(), "")) {
            return new ResponseEntity<>(AuthenticationResponse.builder()
                    .message("User updated successfully")
                    .build()
                    , HttpStatusCode.valueOf(200));
        } else {
            return authenticate(new AuthenticationRequest(userToBeUpdated.getEmail(), request.getPassword()));
        }
    }

    private String saveProfileImage(User user, MultipartFile profileImage) {


        File uploadRootDir = new File("src/main/resources/static/img/users");

        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }


        try {
            String originalFilename = profileImage.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = user.getFirstname() + "_" + user.getLastname() + extension;
            File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + fileName);


            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(profileImage.getBytes());
            stream.close();
            return serverFile.getName();

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception (e.g., log it or throw a custom exception)
            return null;  // Or throw an exception, depending on your error handling strategy
        }
    }

}
