package com.app.rateuniversityapplicationapi.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FileControllerTest {

    private MockMvc mockMvc;
    private FileController fileController;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        fileController = new FileController();
        mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();
    }

    @Test
    void getImageTest() throws Exception {
        String testFileName = "test-image.jpg";
        File tempFile = createTempImageFile(testFileName);

        // Get the byte array of the file
        byte[] expectedBytes = getBytesFromFile(tempFile);

        mockMvc.perform(get("/images/" + testFileName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andExpect(content().bytes(expectedBytes));
    }


    private File createTempImageFile(String filename) throws IOException {
        File tempFile = tempDir.resolve(filename).toFile();
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(new byte[]{/* image content bytes */});
        }
        return tempFile;
    }

    private byte[] getBytesFromFile(File file) throws IOException {
        return org.apache.commons.io.FileUtils.readFileToByteArray(file);
    }
}
