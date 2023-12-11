package com.app.rateuniversityapplicationapi.service;

import com.app.rateuniversityapplicationapi.config.JwtService;
import com.app.rateuniversityapplicationapi.dto.AuthenticationRequest;
import com.app.rateuniversityapplicationapi.dto.AuthenticationResponse;
import com.app.rateuniversityapplicationapi.dto.RegisterRequest;
import com.app.rateuniversityapplicationapi.entity.Role;
import com.app.rateuniversityapplicationapi.entity.User;
import com.app.rateuniversityapplicationapi.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

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
                    .profilePhotoURL(request.getProfilePhotoURL())
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
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail());

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
