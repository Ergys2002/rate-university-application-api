package com.app.rateuniversityapplicationapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseSchedule {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "lecture_hall")
    private String lectureHall;

    @Column(name = "course_date")
    private LocalDate courseDate;

    @Column(name = "course_time")
    private LocalTime courseTime;

    private double duration;

    @Column(name = "course_id")
    protected UUID courseId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "course_id", updatable = false,insertable = false)
    private Course course;
}
