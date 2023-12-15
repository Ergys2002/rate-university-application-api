package com.app.rateuniversityapplicationapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;
import java.util.UUID;

//todo fshi databasen edhe ndrysho firstname dhe lastname

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lecturer {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    private String email;
    private double rating;
    @Column(name="profile_picture")
    private String profilePicture;
    private String description;

    @OneToMany(mappedBy = "lecturer")
    private Set<Course> courses;

}
