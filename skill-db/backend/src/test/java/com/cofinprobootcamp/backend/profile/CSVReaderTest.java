package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

class CSVReaderTest {
    @Mock
    private ProfileService profileService;
    @Mock
    private UserService userService;
    @Mock
    private MultipartFile file;
    private CSVReader csvReader;
    @BeforeEach
    void initialize() {
        MockitoAnnotations.openMocks(this);
        this.csvReader = new CSVReader(file, profileService, userService);
    }
    @Test
    void readProfileFromFile() {

    }
}
