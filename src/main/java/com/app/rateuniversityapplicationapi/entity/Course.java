package com.app.rateuniversityapplicationapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "course_name")
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Column(name = "is_Available")
    private Boolean isAvailable;
    private int totalQuotes;
    private int freeQuotes;
    private double courseRating;

    @Column(name = "lecturer_id")
    protected UUID lecturerId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "lecturer_id", updatable = false,insertable = false)
    private Lecturer lecturer;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> registeredStudents;
}
