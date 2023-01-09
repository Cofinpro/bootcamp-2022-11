package com.cofinprobootcamp.backend.profile;

import com.cofinprobootcamp.backend.exceptions.CSVArgumentNotValidException;
import com.cofinprobootcamp.backend.exceptions.JobTitleNotFoundException;
import com.cofinprobootcamp.backend.profile.dto.ProfileCreateInDTO;
import com.cofinprobootcamp.backend.user.User;
import com.cofinprobootcamp.backend.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
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
    @DisplayName("Happy path for reading Single profile Unit test")
    void readProfileFromFileHappyPath() throws IOException, JobTitleNotFoundException {
        String email = "markus.kremer@cofinpro.de";
        String name = "Markus";
        String surname = "Kremer";
        String jobTitle = "Consultant";
        String degree = "M.Sc";
        String primary = "Technologie";
        String reference = "ref";
        String skills = "skill1;skill2";
        String phoneNumber = "111111111111";
        String birthdate = "1997-10-10";
        String contentOfFile = "Email,Vorname,Nachname,JobTitel,Abschluss,Primärkompetenz,Referenzen,Skills,Telefonnummer,Geburtsdatum\n" +
                email + "," + name + "," + surname + "," + jobTitle + "," + degree + "," + primary + ","  + reference +"," + skills + "," + phoneNumber + "," + birthdate;
        Mockito.when(file.getBytes()).thenReturn(contentOfFile.getBytes());
        ArgumentCaptor< ProfileCreateInDTO > dtoArgumentCaptor = ArgumentCaptor.forClass(ProfileCreateInDTO.class);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        User user = new User();
        Mockito.when(userService.getUserByUsername(email)).thenReturn(user);

        csvReader.readProfileFromFile();

        Mockito.verify(profileService,Mockito.times(1))
                .createProfile(dtoArgumentCaptor.capture(), userArgumentCaptor.capture());
        assertThat(dtoArgumentCaptor.getValue().email()).isEqualTo(email);
        assertThat(dtoArgumentCaptor.getValue().firstName()).isEqualTo(name);
        assertThat(dtoArgumentCaptor.getValue().lastName()).isEqualTo(surname);
        assertThat(dtoArgumentCaptor.getValue().jobTitle()).isEqualTo(jobTitle);
        assertThat(dtoArgumentCaptor.getValue().degree()).isEqualTo(degree);
        assertThat(dtoArgumentCaptor.getValue().primaryExpertise()).isEqualTo(primary);
        assertThat(dtoArgumentCaptor.getValue().referenceText()).isEqualTo(reference);
        assertThat(dtoArgumentCaptor.getValue().skills()).isEqualTo(List.of("skill1","skill2"));
        assertThat(dtoArgumentCaptor.getValue().phoneNumber()).isEqualTo(phoneNumber);
        assertThat(dtoArgumentCaptor.getValue().birthDate()).isEqualTo(birthdate);
        assertThat(userArgumentCaptor.getValue()).isEqualTo(user);
    }
    @Test
    @DisplayName("Error path for reading Single profile Unit test")
    void readProfileFromFileSadPath() throws IOException, JobTitleNotFoundException {
        String email = "markus.kremer@cofinpro.de";
        String name = "Markus";
        String surname = "";
        String jobTitle = "Consultant";
        String degree = "M.Sc";
        String primary = "Technologie";
        String reference = "ref";
        String skills = "skill1;skill2";
        String phoneNumber = "111111111111";
        String birthdate = "1997-10-10";
        String contentOfFile = "Email,Vorname,Nachname,JobTitel,Abschluss,Primärkompetenz,Referenzen,Skills,Telefonnummer,Geburtsdatum\n" +
                email + "," + name + "," + surname + "," + jobTitle + "," + degree + "," + primary + "," + reference + "," + skills + "," + phoneNumber + "," + birthdate;
        Mockito.when(file.getBytes()).thenReturn(contentOfFile.getBytes());
        ArgumentCaptor<ProfileCreateInDTO> dtoArgumentCaptor = ArgumentCaptor.forClass(ProfileCreateInDTO.class);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        User user = new User();
        Mockito.when(userService.getUserByUsername(email)).thenReturn(user);

        assertThrows(CSVArgumentNotValidException.class, () ->{csvReader.readProfileFromFile();});
    }
}
