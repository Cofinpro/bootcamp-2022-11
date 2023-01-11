package com.cofinprobootcamp.backend.image;

import com.cofinprobootcamp.backend.email.EmailSendService;
import com.cofinprobootcamp.backend.exceptions.ImageFormatNotAllowedException;
import com.cofinprobootcamp.backend.exceptions.MailNotSentException;
import com.cofinprobootcamp.backend.exceptions.ProfileNotFoundException;
import com.cofinprobootcamp.backend.profile.Profile;
import com.cofinprobootcamp.backend.profile.ProfileRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Objects;

import static java.util.Objects.isNull;

@Service
public class ImageService {
    private static final String PNG = "image/png";
    private static final String JPG = "image/jpg";
    private static final String JPEG = "image/jpeg";
    private static final String NOIMAGE = "none";
    private final ImageRepository imageRepository;
    private final ProfileRepository profileRepository;
    private final EmailSendService emailSendService;
    public ImageService(ImageRepository imageRepository,
                        ProfileRepository profileRepository,
                        EmailSendService emailSendService) {
        this.imageRepository = imageRepository;
        this.profileRepository = profileRepository;
        this.emailSendService = emailSendService;
    }


    public Image saveImage(String base64Image) throws ImageFormatNotAllowedException {
        if (base64Image == null || base64Image.equals("")) {
            Image image = new Image();
            image.setPrefix(NOIMAGE);
            image.setData("".getBytes());
            return imageRepository.save(image);
        }
        Image image = convertBase64ToImage(base64Image);
        return imageRepository.save(image);
    }

    public Image updateImageIfGiven(String base64Image, Long profilePicId) throws ImageFormatNotAllowedException {
        if (base64Image == null || base64Image.equals("")) {
            return imageRepository.findById(profilePicId).get();
        } else {
            Image image = convertBase64ToImage(base64Image);
            image.setId(profilePicId);
            return image;
        }

    }

    private Image convertBase64ToImage(String base64Image) throws ImageFormatNotAllowedException {
        String prefix = base64Image.split("[,;:]")[1];
        checkImageFormatElseThrowException(prefix);
        byte[] imageData = Base64.getDecoder()
                .decode(base64Image.split(",")[1]);
        Image image = new Image();
        image.setData(imageData);
        image.setPrefix(prefix);
        return image;
    }

    public void resetImage(String profileId) throws MailNotSentException, ProfileNotFoundException {
        Profile current = profileRepository
                .findFirstByOuterId(profileId)
                .orElseThrow(ProfileNotFoundException::new);
        Image image = current.getProfilePic();
        if (image.getPrefix().equals(NOIMAGE)) return;
        image.setData("".getBytes());
        image.setPrefix(NOIMAGE);
        imageRepository.save(image);
        //mailsendingstuff
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

    private void sendProfileUpdateMail(String mailRecipientFullName, String changingUserEmailAddress, String mailRecipientEmailAddress) {
        // trigger info-email
        String day = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String time = LocalTime.now().toString().substring(0, 5);

        String mailText = "Hallo " + mailRecipientFullName + ",\n\n" +
                "dein Profil in der Cofinpro Skill-DB wurde am " + day + " um " + time + " Uhr von " + changingUserEmailAddress + " geändert.";

        emailSendService.sendSimpleMessage(mailRecipientEmailAddress, "Profilupdate", mailText);
    }

    public void deleteImageById(Long id) {
        System.out.println(id);
        imageRepository.deleteById(id);
    }

    public Image getImage(Long id) {
        if (isNull(id)) return null;
        return imageRepository.findById(id).orElse(null);
    }

    private void checkImageFormatElseThrowException(String prefix) throws ImageFormatNotAllowedException {
        if (!(Objects.equals(prefix, PNG) || Objects.equals(prefix, JPG) || Objects.equals(prefix, JPEG))) {
            throw new ImageFormatNotAllowedException();
        }
    }
}
