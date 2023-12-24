package com.app.rateuniversityapplicationapi.config;

import com.app.rateuniversityapplicationapi.dto.responses.LecturerResponse;
import com.app.rateuniversityapplicationapi.entity.Course;
import com.app.rateuniversityapplicationapi.entity.CourseSchedule;
import com.app.rateuniversityapplicationapi.entity.Lecturer;
import com.app.rateuniversityapplicationapi.repository.CourseRepository;
import com.app.rateuniversityapplicationapi.repository.CourseScheduleRepository;
import com.app.rateuniversityapplicationapi.repository.LecturerRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Data
@AllArgsConstructor
public class InitialDataPersistance implements CommandLineRunner {

    private final LecturerRepository lecturerRepository;
    private final CourseRepository courseRepository;
    private final CourseScheduleRepository courseScheduleRepository;

    @Override
    public void run(String... args) throws Exception {
        seedData();
    }

    private List<String> getLecturerIds() {
        return lecturerRepository.getAll().stream().map(LecturerResponse::getId).collect(Collectors.toList());
    }

    private List<String> getCourseIds() {
        return courseRepository.getAll().stream().map(course -> course.getId().toString()).collect(Collectors.toList());
    }

    public void seedData() {

        createLecturerIfNotPresent(
                "John",
                "Doe",
                "johndoe@example.com",
                "https://example.com/profiles/johndoe.jpg",
                "Experienced lecturer in mathematics"
        );

        createLecturerIfNotPresent(
                "Alice",
                "Smith",
                "alice.smith@example.com",
                "https://example.com/profiles/alicesmith.jpg",
                "Specializes in literature and language studies"
        );

        createLecturerIfNotPresent(
                "Michael",
                "Johnson",
                "michael.johnson@example.com",
                "https://example.com/profiles/michaeljohnson.jpg",
                "Teaching computer science for a decade"
        );

        createLecturerIfNotPresent(
                "Emily",
                "Brown",
                "emily.brown@example.com",
                "https://example.com/profiles/emilybrown.jpg",
                "Passionate about environmental sciences"
        );

        createLecturerIfNotPresent(
                "Daniel",
                "Garcia",
                "daniel.garcia@example.com",
                "https://example.com/profiles/danielgarcia.jpg",
                "Expertise in business administration"
        );

        createLecturerIfNotPresent(
                "Sophia",
                "Martinez",
                "sophia.martinez@example.com",
                "https://example.com/profiles/sophiamartinez.jpg",
                "Innovative approaches to psychology teaching"
        );

        createLecturerIfNotPresent(
                "William",
                "Lee",
                "william.lee@example.com",
                "https://example.com/profiles/williamlee.jpg",
                "Leadership training and management studies"
        );

        createLecturerIfNotPresent(
                "Olivia",
                "Wang",
                "olivia.wang@example.com",
                "https://example.com/profiles/oliviawang.jpg",
                "Research-focused lecturer in biology"
        );

        createLecturerIfNotPresent(
                "Ethan",
                "Lopez",
                "ethan.lopez@example.com",
                "https://example.com/profiles/ethanlopez.jpg",
                "History and cultural studies enthusiast"
        );

        createLecturerIfNotPresent(
                "Ava",
                "Nguyen",
                "ava.nguyen@example.com",
                "https://example.com/profiles/avanguyen.jpg",
                "Advocating for gender studies and equality"
        );

        createLecturerIfNotPresent(
                "Liam",
                "Gonzalez",
                "liam.gonzalez@example.com",
                "https://example.com/profiles/liamgonzalez.jpg",
                "Expertise in physics and theoretical studies"
        );

        createLecturerIfNotPresent(
                "Charlotte",
                "Turner",
                "charlotte.turner@example.com",
                "https://example.com/profiles/charlotteturner.jpg",
                "Mathematics enthusiast and educator"
        );

        createLecturerIfNotPresent(
                "Mason",
                "Perez",
                "mason.perez@example.com",
                "https://example.com/profiles/masonperez.jpg",
                "Astronomy and astrophysics researcher"
        );

        createLecturerIfNotPresent(
                "Amelia",
                "Hernandez",
                "amelia.hernandez@example.com",
                "https://example.com/profiles/ameliahernandez.jpg",
                "Music theory and composition specialist"
        );

        createLecturerIfNotPresent(
                "Logan",
                "Scott",
                "logan.scott@example.com",
                "https://example.com/profiles/loganscott.jpg",
                "Physical education and sports science mentor"
        );

        createLecturerIfNotPresent(
                "Grace",
                "Flores",
                "grace.flores@example.com",
                "https://example.com/profiles/graceflores.jpg",
                "Healthcare administration and public policy advocate"
        );

        createLecturerIfNotPresent(
                "Ryan",
                "Adams",
                "ryan.adams@example.com",
                "https://example.com/profiles/ryanadams.jpg",
                "Philosophy and ethics lecturer"
        );

        createLecturerIfNotPresent(
                "Chloe",
                "Russell",
                "chloe.russell@example.com",
                "https://example.com/profiles/chloerussell.jpg",
                "Economics and global finance expert"
        );

        createLecturerIfNotPresent(
                "Lucas",
                "Griffin",
                "lucas.griffin@example.com",
                "https://example.com/profiles/lucasgriffin.jpg",
                "Technology and innovation evangelist"
        );

        createLecturerIfNotPresent(
                "Zoe",
                "Diaz",
                "zoe.diaz@example.com",
                "https://example.com/profiles/zoediaz.jpg",
                "Creative writing and storytelling mentor"
        );


        List<String> lecturerIds = getLecturerIds();

        createCourseIfNotPresent(
                "Introduction to Astrophysics",
                "Explore the wonders of the universe and the laws governing celestial objects.",
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 2, 28),
                true,
                10,
                UUID.fromString(lecturerIds.get(0))
        );

        createCourseIfNotPresent(
                "Advanced Calculus and Differential Equations",
                "Master the intricacies of advanced mathematical concepts and applications.",
                LocalDate.of(2023, 3, 1),
                LocalDate.of(2023, 4, 30),
                true,
                12,
                UUID.fromString(lecturerIds.get(1))
        );

        createCourseIfNotPresent(
                "Genetics and Evolution",
                "Dive into the study of genes, inheritance, and the mechanisms of evolution.",
                LocalDate.of(2023, 5, 1),
                LocalDate.of(2023, 6, 30),
                true,
                8,
                UUID.fromString(lecturerIds.get(2))
        );

        createCourseIfNotPresent(
                "Introduction to Psychology",
                "Explore the fundamentals of human behavior and psychological processes.",
                LocalDate.of(2023, 7, 1),
                LocalDate.of(2023, 8, 31),
                true,
                9,
                UUID.fromString(lecturerIds.get(3))

        );

        createCourseIfNotPresent(
                "Data Science for Beginners",
                "Learn the basics of data analysis, visualization, and interpretation.",
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 10, 31),
                true,
                11,
                UUID.fromString(lecturerIds.get(4))
        );

        createCourseIfNotPresent(
                "Artificial Intelligence Fundamentals",
                "Get introduced to AI concepts, algorithms, and their real-world applications.",
                LocalDate.of(2023, 11, 1),
                LocalDate.of(2023, 12, 31),
                true,
                9,
                UUID.fromString(lecturerIds.get(5))
        );

        createCourseIfNotPresent(
                "Financial Markets and Investments",
                "Explore the dynamics of financial markets, investment strategies, and risk management.",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 2, 29),
                true,
                10,
                UUID.fromString(lecturerIds.get(6))
        );

        createCourseIfNotPresent(
                "Digital Marketing Essentials",
                "Learn the core principles of digital marketing and effective online strategies.",
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 4, 30),
                true,
                11,
                UUID.fromString(lecturerIds.get(7))
        );

        createCourseIfNotPresent(
                "Creative Writing Workshop",
                "Enhance your writing skills through exercises and techniques in this creative writing workshop.",
                LocalDate.of(2024, 5, 1),
                LocalDate.of(2024, 6, 30),
                true,
                8,
                UUID.fromString(lecturerIds.get(8))
        );

        createCourseIfNotPresent(
                "Photography Masterclass",
                "Learn photography techniques, composition, and editing in this comprehensive masterclass.",
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 8, 31),
                true,
                12,
                UUID.fromString(lecturerIds.get(9))
        );


        createCourseIfNotPresent(
                "Entrepreneurship Fundamentals",
                "Discover the essentials of starting and managing your own business.",
                LocalDate.of(2024, 9, 1),
                LocalDate.of(2024, 10, 31),
                true,
                13,
                UUID.fromString(lecturerIds.get(9))
        );

        createCourseIfNotPresent(
                "Neuroscience and Cognition",
                "Explore the brain's structure, functions, and cognitive processes.",
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 12, 31),
                true,
                10,
                UUID.fromString(lecturerIds.get(8))
        );

        createCourseIfNotPresent(
                "Environmental Sustainability",
                "Learn about environmental issues, conservation, and sustainable practices.",
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 2, 28),
                true,
                9,
                UUID.fromString(lecturerIds.get(7))
        );

        createCourseIfNotPresent(
                "Mobile App Development",
                "Master the art of creating mobile applications for different platforms.",
                LocalDate.of(2025, 3, 1),
                LocalDate.of(2025, 4, 30),
                true,
                11,
                UUID.fromString(lecturerIds.get(6))
        );

        createCourseIfNotPresent(
                "Forensic Science and Criminalistics",
                "Explore forensic techniques and investigative methods used in solving crimes.",
                LocalDate.of(2025, 5, 1),
                LocalDate.of(2025, 6, 30),
                true,
                8,
                UUID.fromString(lecturerIds.get(5))
        );

        createCourseIfNotPresent(
                "Cryptocurrency and Blockchain",
                "Understand the technology behind cryptocurrencies and the blockchain.",
                LocalDate.of(2025, 7, 1),
                LocalDate.of(2025, 8, 31),
                true,
                12,
                UUID.fromString(lecturerIds.get(4))
        );

        createCourseIfNotPresent(
                "Public Speaking Mastery",
                "Develop effective public speaking skills and overcome stage fright.",
                LocalDate.of(2025, 9, 1),
                LocalDate.of(2025, 10, 31),
                true,
                10,
                UUID.fromString(lecturerIds.get(3))
        );

        createCourseIfNotPresent(
                "Ethical Hacking and Cybersecurity",
                "Learn ethical hacking techniques and cybersecurity fundamentals.",
                LocalDate.of(2025, 11, 1),
                LocalDate.of(2025, 12, 31),
                true,
                11,
                UUID.fromString(lecturerIds.get(2))
        );

        createCourseIfNotPresent(
                "Music Production and Audio Engineering",
                "Explore the art and technology behind music production and audio engineering.",
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 2, 28),
                true,
                10,
                UUID.fromString(lecturerIds.get(1))
        );

        createCourseIfNotPresent(
                "Machine Learning and Neural Networks",
                "Delve into the principles of machine learning and neural network architectures.",
                LocalDate.of(2026, 3, 1),
                LocalDate.of(2026, 4, 30),
                true,
                12,
                UUID.fromString(lecturerIds.get(0))
        );

        List<String> courseIds = getCourseIds();

        createCourseScheduleIfNotPresent(
                "FTI104",
                LocalDate.parse("2024-01-06"),
                LocalTime.parse("09:00:00"),
                3,
                UUID.fromString(courseIds.get(0))
        );

        createCourseScheduleIfNotPresent(
                "FTI105",
                LocalDate.parse("2024-01-06"),
                LocalTime.parse("10:00:00"),
                2,
                UUID.fromString(courseIds.get(1))
        );
        createCourseScheduleIfNotPresent(
                "FTI106",
                LocalDate.parse("2024-01-06"),
                LocalTime.parse("11:00:00"),
                1,
                UUID.fromString(courseIds.get(2))
        );
        createCourseScheduleIfNotPresent(
                "FTI107",
                LocalDate.parse("2024-01-06"),
                LocalTime.parse("12:00:00"),
                3,
                UUID.fromString(courseIds.get(3))
        );
        createCourseScheduleIfNotPresent(
                "FTI108",
                LocalDate.parse("2024-01-06"),
                LocalTime.parse("13:00:00"),
                3,
                UUID.fromString(courseIds.get(4))
        );
        createCourseScheduleIfNotPresent(
                "FTI109",
                LocalDate.parse("2024-01-06"),
                LocalTime.parse("14:00:00"),
                3,
                UUID.fromString(courseIds.get(5))
        );
        createCourseScheduleIfNotPresent(
                "FTI111",
                LocalDate.parse("2024-01-06"),
                LocalTime.parse("15:00:00"),
                3,
                UUID.fromString(courseIds.get(6))
        );
        createCourseScheduleIfNotPresent(
                "FIE205",
                LocalDate.parse("2024-01-06"),
                LocalTime.parse("16:00:00"),
                3,
                UUID.fromString(courseIds.get(7))
        );
        createCourseScheduleIfNotPresent(
                "FIE210",
                LocalDate.parse("2024-01-06"),
                LocalTime.parse("17:00:00"),
                3,
                UUID.fromString(courseIds.get(8))
        );
        createCourseScheduleIfNotPresent(
                "FIE307",
                LocalDate.parse("2024-01-07"),
                LocalTime.parse("08:00:00"),              //
                3,
                UUID.fromString(courseIds.get(9))
        );
        createCourseScheduleIfNotPresent(
                "FIE304",
                LocalDate.parse("2024-01-07"),
                LocalTime.parse("09:00:00"),
                3,
                UUID.fromString(courseIds.get(10))
        );
        createCourseScheduleIfNotPresent(
                "UPT202",
                LocalDate.parse("2024-01-07"),
                LocalTime.parse("10:00:00"),
                3,
                UUID.fromString(courseIds.get(11))
        );
        createCourseScheduleIfNotPresent(
                "UPT207",
                LocalDate.parse("2024-01-07"),
                LocalTime.parse("11:00:00"),
                3,
                UUID.fromString(courseIds.get(12))
        );
        createCourseScheduleIfNotPresent(
                "UPT209",
                LocalDate.parse("2024-01-07"),
                LocalTime.parse("12:00:00"),
                3,
                UUID.fromString(courseIds.get(13))
        );
        createCourseScheduleIfNotPresent(
                "UPT203",
                LocalDate.parse("2024-01-07"),
                LocalTime.parse("13:00:00"),
                3,
                UUID.fromString(courseIds.get(14))
        );
        createCourseScheduleIfNotPresent(
                "FTI305",
                LocalDate.parse("2024-01-07"),
                LocalTime.parse("14:00:00"),
                3,
                UUID.fromString(courseIds.get(15))
        );
        createCourseScheduleIfNotPresent(
                "FTI214",
                LocalDate.parse("2024-01-07"),
                LocalTime.parse("15:00:00"),
                3,
                UUID.fromString(courseIds.get(16))
        );
        createCourseScheduleIfNotPresent(
                "FTI315",
                LocalDate.parse("2024-01-07"),
                LocalTime.parse("16:00:00"),
                3,
                UUID.fromString(courseIds.get(17))
        );
        createCourseScheduleIfNotPresent(
                "FIE217",
                LocalDate.parse("2024-01-07"),
                LocalTime.parse("17:00:00"),
                3,
                UUID.fromString(courseIds.get(18))
        );
        createCourseScheduleIfNotPresent(
                "FIE211",
                LocalDate.parse("2024-01-08"),
                LocalTime.parse("08:00:00"),
                3,
                UUID.fromString(courseIds.get(19))
        );
        createCourseScheduleIfNotPresent(
                "FIE213",
                LocalDate.parse("2024-01-08"),
                LocalTime.parse("09:00:00"),
                3,
                UUID.fromString(courseIds.get(0))
        );
        createCourseScheduleIfNotPresent(
                "FIE117",
                LocalDate.parse("2024-01-08"),
                LocalTime.parse("10:00:00"),
                3,
                UUID.fromString(courseIds.get(1))
        );
        createCourseScheduleIfNotPresent(
                "UPT102",
                LocalDate.parse("2024-01-08"),
                LocalTime.parse("11:00:00"),
                3,
                UUID.fromString(courseIds.get(2))
        );
        createCourseScheduleIfNotPresent(
                "UPT127",
                LocalDate.parse("2024-01-08"),
                LocalTime.parse("12:00:00"),
                3,
                UUID.fromString(courseIds.get(3))
        );
        createCourseScheduleIfNotPresent(
                "UPT112",
                LocalDate.parse("2024-01-08"),
                LocalTime.parse("13:00:00"),
                3,
                UUID.fromString(courseIds.get(4))
        );
        createCourseScheduleIfNotPresent(
                "UPT123",
                LocalDate.parse("2024-01-08"),
                LocalTime.parse("14:00:00"),
                3,
                UUID.fromString(courseIds.get(5))
        );
        createCourseScheduleIfNotPresent(
                "UPT115",
                LocalDate.parse("2024-01-08"),
                LocalTime.parse("15:00:00"),
                3,
                UUID.fromString(courseIds.get(6))
        );
        createCourseScheduleIfNotPresent(
                "UPT117",
                LocalDate.parse("2024-01-08"),
                LocalTime.parse("16:00:00"),
                3,
                UUID.fromString(courseIds.get(7))
        );createCourseScheduleIfNotPresent(
                "UPT222",
                LocalDate.parse("2024-01-08"),
                LocalTime.parse("17:00:00"),
                3,
                UUID.fromString(courseIds.get(8))
        );
        createCourseScheduleIfNotPresent(
                "UPT333",
                LocalDate.parse("2024-01-09"),
                LocalTime.parse("08:00:00"),
                3,
                UUID.fromString(courseIds.get(9))
        );
        createCourseScheduleIfNotPresent(
                "UPT444",
                LocalDate.parse("2024-01-09"),
                LocalTime.parse("09:00:00"),
                3,
                UUID.fromString(courseIds.get(10))
        );
        createCourseScheduleIfNotPresent(
                "UPT111",
                LocalDate.parse("2024-01-09"),
                LocalTime.parse("10:00:00"),
                3,
                UUID.fromString(courseIds.get(11))
        );createCourseScheduleIfNotPresent(
                "UPT000",
                LocalDate.parse("2024-01-09"),
                LocalTime.parse("11:00:00"),
                3,
                UUID.fromString(courseIds.get(12))
        );
        createCourseScheduleIfNotPresent(
                "FTI000",
                LocalDate.parse("2024-01-09"),
                LocalTime.parse("12:00:00"),
                3,
                UUID.fromString(courseIds.get(13))
        );
        createCourseScheduleIfNotPresent(
                "FTI111",
                LocalDate.parse("2024-01-09"),
                LocalTime.parse("13:00:00"),
                3,
                UUID.fromString(courseIds.get(14))
        );
        createCourseScheduleIfNotPresent(
                "FTI222",
                LocalDate.parse("2024-01-09"),
                LocalTime.parse("14:00:00"),
                3,
                UUID.fromString(courseIds.get(15))
        );
        createCourseScheduleIfNotPresent(
                "FTI333",
                LocalDate.parse("2024-01-09"),
                LocalTime.parse("15:00:00"),
                3,
                UUID.fromString(courseIds.get(16))
        );
        createCourseScheduleIfNotPresent(
                "FTI444",
                LocalDate.parse("2024-01-09"),
                LocalTime.parse("16:00:00"),
                3,
                UUID.fromString(courseIds.get(17))
        );
        createCourseScheduleIfNotPresent(
                "FTI555",
                LocalDate.parse("2024-01-09"),
                LocalTime.parse("17:00:00"),
                3,
                UUID.fromString(courseIds.get(18))
        );
        createCourseScheduleIfNotPresent(
                "FIE000",
                LocalDate.parse("2024-01-10"),
                LocalTime.parse("08:00:00"),
                3,
                UUID.fromString(courseIds.get(19))
        );
        createCourseScheduleIfNotPresent(
                "FIE111",
                LocalDate.parse("2024-01-10"),
                LocalTime.parse("09:00:00"),
                3,
                UUID.fromString(courseIds.get(0))
        );
        createCourseScheduleIfNotPresent(
                "FIE222",
                LocalDate.parse("2024-01-10"),
                LocalTime.parse("10:00:00"),
                3,
                UUID.fromString(courseIds.get(1))
        );
        createCourseScheduleIfNotPresent(
                "FIE333",
                LocalDate.parse("2024-01-10"),
                LocalTime.parse("11:00:00"),
                3,
                UUID.fromString(courseIds.get(2))
        );
        createCourseScheduleIfNotPresent(
                "FIE444",
                LocalDate.parse("2024-01-10"),
                LocalTime.parse("12:00:00"),
                3,
                UUID.fromString(courseIds.get(3))
        );
        createCourseScheduleIfNotPresent(
                "FIE555",
                LocalDate.parse("2024-01-10"),
                LocalTime.parse("13:00:00"),
                3,
                UUID.fromString(courseIds.get(4))
        );
    }


    private void createLecturerIfNotPresent(
            String firstName,
            String lastName,
            String email,
            String profilePicture,
            String description
    ) {

        Lecturer lecturer = Lecturer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .rating(-1)
                .profilePicture(profilePicture)
                .description(description)
                .build();

        Lecturer lecturerDb = lecturerRepository.getLecturersByEmail(lecturer.getEmail());
        if (lecturerDb == null)
            lecturerRepository.save(lecturer);
    }

    public void createCourseIfNotPresent(
            String title,
            String description,
            LocalDate startDate,
            LocalDate endDate,
            boolean isAvailable,
            int totalQuotes,
            UUID lecturerId
    ) {
        Course course = Course.builder()
                .title(title)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .isAvailable(isAvailable)
                .totalQuotes(totalQuotes)
                .enrolledStudents(0)
                .courseRating(-1)
                .lecturerId(lecturerId)
                .build();

        Course courseDB = courseRepository.getCourseByTitleEqualsIgnoreCaseAndDescriptionEqualsIgnoreCaseAndLecturerId(course.getTitle(), course.getDescription(), course.getLecturerId());
        if (courseDB == null)
            courseRepository.save(course);

    }

    private void createCourseScheduleIfNotPresent(
            String lectureHall,
            LocalDate courseDate,
            LocalTime courseTime,
            double duration,
            UUID courseId
    ) {

        CourseSchedule courseSchedule = CourseSchedule.builder()
                .lectureHall(lectureHall)
                .courseDate(courseDate)
                .courseTime(courseTime)
                .duration(duration)
                .courseId(courseId)
                .build();

        CourseSchedule courseScheduleDb = courseScheduleRepository
                .getCourseScheduleByCourseIdAndCourseDateAndCourseTimeAndDurationAndLectureHall(
                        courseSchedule.getCourseId(),
                        courseSchedule.getCourseDate(),
                        courseSchedule.getCourseTime(),
                        courseSchedule.getDuration(),
                        courseSchedule.getLectureHall()
                );
        if (courseScheduleDb == null)
            courseScheduleRepository.save(courseSchedule);
    }

}
