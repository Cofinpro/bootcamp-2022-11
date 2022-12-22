//package com.cofinprobootcamp.backend.profile;
//
//import com.cofinprobootcamp.backend.config.Regex;
//import com.cofinprobootcamp.backend.exceptions.JobTitleNotFoundException;
//import com.cofinprobootcamp.backend.profile.dto.ProfileCreateInDTO;
//import com.cofinprobootcamp.backend.user.User;
//import com.cofinprobootcamp.backend.user.UserService;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.LinkedList;
//import java.util.List;
//
//@Component
//@Order(4)
//public class ProfilesSetup {
//    private final UserService userService;
//    private final ProfileService profileService;
//
//    public ProfilesSetup(UserService userService, ProfileService profileService) {
//        this.userService = userService;
//        this.profileService = profileService;
//    }
//
//    @PostConstruct
//    public void init() {
//        initTestProfiles();
//    }
//
//    private void initTestProfiles() {
//        List<ProfileCreateInDTO> testProfiles = new LinkedList<>();
//        testProfiles.add(profileDTOFromMinimalInfo(
//                emailString("lennart.rehmer"),
//                "Lennart", "Rehmer",
//                "Consultant", "M.Sc.",
//                "1993-04-21"));
//        testProfiles.add(profileDTOFromMinimalInfo(
//                emailString("markus.kremer"),
//                "Markus",
//                "Kremer",
//                "Consultant",
//                "M.Sc.",
//                "1913-01-02"));
//        testProfiles.add(profileDTOFromMinimalInfo(
//                emailString("luis.geyer"),
//                "Luis",
//                "Geyer",
//                "Consultant",
//                "B.Sc.",
//                "2019-12-12"));
//        testProfiles.add(profileDTOFromMinimalInfo(
//                emailString("thorben.dreier"),
//                "Thorben",
//                "Dreier",
//                "Consultant",
//                "B.Sc.",
//                "1994-05-05"));
//        testProfiles.add(profileDTOFromMinimalInfo(
//                emailString("theresa.riesterer"),
//                "Theresa",
//                "Riesterer",
//                "Expert Consultant",
//                "M.Sc.",
//                "1999-02-06"));
//        testProfiles.forEach(this::createOneTestProfile);
//    }
//
//    private String emailString(String name) {
//        return name + "@cofinpro.de";
//    }
//
//    private void createOneTestProfile(ProfileCreateInDTO inDTO) {
//        User user = userService.getUserByUsername(inDTO.email());
//        try {
//            Profile profile = profileService.createProfile(inDTO, user);
//            userService.assignProfileToUser(user, profile);
//        } catch (JobTitleNotFoundException jtnfe) {
//            System.out.println("Profile initialization failed.");
//        }
//    }
//
//    private ProfileCreateInDTO profileDTOFromMinimalInfo(String email, String firstName, String lastName, String job, String degree, String birthDate) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Regex.DATE_FORMAT);
//        LocalDate localDate = LocalDate.parse(birthDate, formatter);
//        return new ProfileCreateInDTO(email, firstName, lastName, job, degree, "Technologie", "Bootcamp (10-2022)", List.of(), "12345678901", localDate);
//    }
//}
