package com.app.rateuniversityapplicationapi.service.classes;

import com.app.rateuniversityapplicationapi.dto.responses.LecturerResponse;
import com.app.rateuniversityapplicationapi.entity.Lecturer;
import com.app.rateuniversityapplicationapi.repository.LecturerRepository;
import com.app.rateuniversityapplicationapi.service.classes.LecturerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LecturerServiceTest {

    @Mock
    private LecturerRepository lecturerRepository;

    @InjectMocks
    private LecturerService lecturerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private LecturerResponse createMockLecturerResponse(Lecturer lecturer) {
        // Create a mock LecturerResponse from a Lecturer entity.
        // This needs to be implemented based on how LecturerResponse is supposed to be created.
        return new LecturerResponse() {
            @Override
            public String getId() {
                return lecturer.getId().toString();
            }

            @Override
            public String getFirstName() {
                return lecturer.getFirstName();
            }

            @Override
            public String getLastName() {
                return lecturer.getLastName();
            }

            @Override
            public String getEmail() {
                return lecturer.getEmail();
            }

            @Override
            public double getRating() {
                return lecturer.getRating();
            }

            @Override
            public String getDescription() {
                return lecturer.getDescription();
            }

            @Override
            public String getProfilePicture() {
                return lecturer.getProfilePicture();
            }

        };
    }

    @Test
    void testGetAllLecturers() {
        List<Lecturer> mockLecturers = IntStream.range(0, 5)
                .mapToObj(i -> Lecturer.builder()
                        .id(UUID.randomUUID())
                        .firstName("FirstName" + i)
                        .lastName("LastName" + i)
                        .email("email" + i + "@example.com")
                        .rating(4.5 + i)
                        .profilePicture("profilePictureUrl" + i)
                        .description("Description" + i)
                        .build())
                .collect(Collectors.toList());

        List<LecturerResponse> mockLecturerResponses = mockLecturers.stream()
                .map(this::createMockLecturerResponse)
                .collect(Collectors.toList());

        when(lecturerRepository.getAll()).thenReturn(mockLecturerResponses);

        List<LecturerResponse> result = lecturerService.getAllLecturers();

        assertNotNull(result);
        assertEquals(mockLecturers.size(), result.size());
        for (int i = 0; i < mockLecturers.size(); i++) {
            LecturerResponse response = result.get(i);
            Lecturer lecturer = mockLecturers.get(i);
            assertEquals(lecturer.getId().toString(), response.getId());
            assertEquals(lecturer.getFirstName(), response.getFirstName());
            assertEquals(lecturer.getLastName(), response.getLastName());
            assertEquals(lecturer.getEmail(), response.getEmail());
            assertEquals(lecturer.getRating(), response.getRating());
            assertEquals(lecturer.getDescription(), response.getDescription());
            assertEquals(lecturer.getProfilePicture(), response.getProfilePicture());
        }

        verify(lecturerRepository).getAll();
    }

    @Test
    void testGetLecturerById() {
        UUID sampleId = UUID.randomUUID();
        Lecturer mockLecturer = Lecturer.builder()
                .id(sampleId)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .rating(4.5)
                .profilePicture("profilePictureUrl")
                .description("A brief description")
                .build();

        when(lecturerRepository.getLecturerById(sampleId)).thenReturn(mockLecturer);


        LecturerResponse response = lecturerService.getLecturerById(sampleId);

        assertNotNull(response);
        assertEquals(mockLecturer.getId().toString(), response.getId());
        assertEquals(mockLecturer.getFirstName(), response.getFirstName());
        assertEquals(mockLecturer.getLastName(), response.getLastName());
        assertEquals(mockLecturer.getEmail(), response.getEmail());
        assertEquals(mockLecturer.getRating(), response.getRating());
        assertEquals(mockLecturer.getDescription(), response.getDescription());
        assertEquals(mockLecturer.getProfilePicture(), response.getProfilePicture());

        verify(lecturerRepository).getLecturerById(sampleId);
    }

    @Test
    void testGetNumberOfLecturers() {
        int numberOfLecturers = 5;
        when(lecturerRepository.getNumberOfLecturers()).thenReturn(numberOfLecturers);

        int result = lecturerService.getNumberOfLecturers();

        assertEquals(numberOfLecturers, result);
        verify(lecturerRepository).getNumberOfLecturers();
    }

    @Test
    void testGetLecturerIds() {
        List<Lecturer> mockLecturers = IntStream.range(0, 5)
                .mapToObj(i -> Lecturer.builder()
                        .id(UUID.randomUUID())
                        .firstName("FirstName" + i)
                        .lastName("LastName" + i)
                        .email("email" + i + "@example.com")
                        .rating(4.5 + i)
                        .profilePicture("profilePictureUrl" + i)
                        .description("Description" + i)
                        .build())
                .collect(Collectors.toList());

        List<LecturerResponse> mockLecturerResponses = mockLecturers.stream()
                .map(this::createMockLecturerResponse)
                .collect(Collectors.toList());

        when(lecturerRepository.getAll()).thenReturn(mockLecturerResponses);

        List<String> result = lecturerService.getLecturerIds();

        assertNotNull(result);
        assertEquals(mockLecturers.size(), result.size());
        result.forEach(id -> assertTrue(mockLecturers.stream()
                .anyMatch(lecturer -> lecturer.getId().toString().equals(id))));

        verify(lecturerRepository).getAll();
    }
}
