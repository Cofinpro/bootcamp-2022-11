package com.cofinprobootcamp.backend.image;

import com.cofinprobootcamp.backend.email.EmailSendService;
import com.cofinprobootcamp.backend.exceptions.ImageFormatNotAllowedException;
import com.cofinprobootcamp.backend.exceptions.MailNotSentException;
import com.cofinprobootcamp.backend.exceptions.ProfileNotFoundException;
import com.cofinprobootcamp.backend.image.dto.*;
import com.cofinprobootcamp.backend.profile.Profile;
import com.cofinprobootcamp.backend.profile.ProfileController;
import com.cofinprobootcamp.backend.profile.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Objects;


@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService imageService;
    private final ProfileRepository profileRepository;
    private final ImageRepository imageRepository;
    private final EmailSendService emailSendService;

    @Autowired
    public ImageController(ImageService imageService,
                           ProfileRepository profileRepository,
                           ImageRepository imageRepository,
                           EmailSendService emailSendService) {
        this.imageService = imageService;
        this.profileRepository = profileRepository;
        this.imageRepository = imageRepository;
        this.emailSendService = emailSendService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(@authorityPrefix + 'IMAGES_GET_BY_ID')")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Image image = imageService.getImage(id);
        if (!(Objects.equals(image.getPrefix(), "none"))) {
            byte[] imageData = image.getData();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", image.getPrefix());
            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } else {
            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<>(null, headers, HttpStatus.OK);
        }
    }
    @DeleteMapping("/{profileId}")
    @PreAuthorize("hasPermission(#profileId, 'void',@authorityPrefix + 'IMAGES_DELETE_BY_ID')")
    public void deleteImage(@PathVariable String profileId)
            throws ProfileNotFoundException, MailNotSentException {
        Profile current = profileRepository
                .findFirstByOuterId(profileId)
                .orElseThrow(ProfileNotFoundException::new);
        Image image = current.getProfilePic();
        if (!image.getPrefix().equals("none")) {
            image.setData("".getBytes());
            image.setPrefix("none");
            imageRepository.save(image);

            //mailsending
            try {
                String mailRecipientAddress = current.getOwner().getUsername();
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                if (!mailRecipientAddress.equals(authentication.getName())) {
                    // trigger info-email
                    sendProfileUpdateMail(current.getFullName(), authentication.getName(), mailRecipientAddress);
                }
            } catch (Exception e) {
                throw new MailNotSentException();
            }
        }
    }
    private void sendProfileUpdateMail(String mailRecipientFullName, String changingUserEmailAddress, String mailRecipientEmailAddress) {
        String day = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String time = LocalTime.now().toString().substring(0, 5);

        String mailText = "Hallo " + mailRecipientFullName + ",\n\n" +
                "dein Profil in der Cofinpro Skill-DB wurde am " + day + " um " + time + " Uhr von " + changingUserEmailAddress + " geändert.";

        emailSendService.sendSimpleMessage(mailRecipientEmailAddress, "Profilupdate", mailText);
    }
}

