package com.app.rateuniversityapplicationapi.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Lazy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

//Data@ Do not use Data stack overflow error
@Getter
@Setter
@Entity
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
    private LocalDate startDate;
    private LocalDate endDate;
    @Column(name = "is_available")
    private boolean isAvailable;
    private int totalQuotes;
    @Column(name = "enrolle_students")
    private int enrolledStudents;
    private double courseRating;
    private String picture;

    @Column(name = "lecturer_id")
    protected UUID lecturerId;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "lecturer_id", updatable = false,insertable = false)
    private Lecturer lecturer;

//    Changed cascadeType from all to merge
    @JsonBackReference
    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> registeredStudents;

    @JsonBackReference
    @OneToMany(mappedBy = "course")
    private Set<CourseSchedule> courseSchedules;

    @JsonBackReference
    @OneToMany(mappedBy = "course")
    private List<Review> reviews;
}
