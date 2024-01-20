package com.app.rateuniversityapplicationapi.controller;

import com.app.rateuniversityapplicationapi.dto.responses.LecturerResponse;
import com.app.rateuniversityapplicationapi.service.interfaces.ILecturerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LecturerControllerTest {

    @Mock
    private ILecturerService lecturerService;

    @InjectMocks
    private LecturerController lecturerController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(lecturerController).build();
    }

    @Test
    void getAllLecturersTest() throws Exception {
        List<LecturerResponse> lecturers = Arrays.asList(
                new LecturerResponse() {
                    public String getId() { return "123"; }
                    public String getFirstName() { return "John"; }
                    public String getLastName() { return "Doe"; }
                    public String getEmail() { return "johndoe@example.com"; }
                    public double getRating() { return 4.5; }
                    public String getDescription() { return "Experienced Professor"; }
                    public String getProfilePicture() { return "profile.jpg"; }
                }
        );

        when(lecturerService.getAllLecturers()).thenReturn(lecturers);

        mockMvc.perform(get("/api/lecturers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("123"))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"));
    }

    @Test
    void getLecturerByIdTest() throws Exception {
        UUID mockId = UUID.randomUUID();
        LecturerResponse lecturer = new LecturerResponse() {
            public String getId() { return mockId.toString(); }
            public String getFirstName() { return "John"; }

            @Override
            public String getLastName() {
                return null;
            }

            @Override
            public String getEmail() {
                return null;
            }

            @Override
            public double getRating() {
                return 0;
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public String getProfilePicture() {
                return null;
            }
        };

        when(lecturerService.getLecturerById(mockId)).thenReturn(lecturer);

        mockMvc.perform(get("/api/lecturers/" + mockId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(mockId.toString()));
    }

    @Test
    void getNumberOfLecturersTest() throws Exception {
        when(lecturerService.getNumberOfLecturers()).thenReturn(5);

        mockMvc.perform(get("/api/lecturers/number-of-lecturers"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void getLecturerIdsTest() throws Exception {
        List<String> lecturerIds = Arrays.asList("15cf01cb-3054-4d56-9145-38ea9a8933e4",
                "1ae24f11-7a48-4a65-9cd8-8b977c5d30e4",
                "24cc2d5d-2e29-41fd-a8ee-f4f4b1bd13be",
                "358eeb42-deb1-4a5d-aa9a-3d64d21dba4a",
                "38ef352f-9ccd-4dbc-bce2-399c71c091fc",
                "3f9fa5d5-eabd-4ee2-8244-812888b20f1d",
                "4584e103-8f32-43db-b7d1-bef980cebf6d",
                "5e53cb27-66de-4c4f-9e24-1de073ea31f7",
                "67e1110c-6d69-480d-9ea2-37979d1e3ea6",
                "6cdc4d79-b36b-4a89-b3b7-4ea883c695aa",
                "722b708f-3509-42ce-aa11-a056882af079",
                "8084a4b2-c159-433b-b271-97d550376fa4",
                "816749fe-238e-43f3-b425-dbd2c0791a04",
                "9871d4f6-7515-478c-a45b-af405ff32a74",
                "a6383866-aaba-4673-a9a0-157de1c3342c",
                "be24af66-eadf-4930-9e45-7a5c96e15ceb",
                "c71bfe55-0786-455b-8355-a2a6ab38dbe8",
                "cc12323b-af30-4e12-83d7-d1ce8137c90b",
                "f77e77cd-cb41-432f-9e81-b6f1e2bee7e6",
                "fc5c1411-24b7-496f-9e26-02ccdd66666b"

        );
        when(lecturerService.getLecturerIds()).thenReturn(lecturerIds);

        mockMvc.perform(get("/api/lecturers/lecturer-ids"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
