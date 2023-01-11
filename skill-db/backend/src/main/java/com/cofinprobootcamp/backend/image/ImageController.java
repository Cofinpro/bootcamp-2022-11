package com.cofinprobootcamp.backend.image;

import com.cofinprobootcamp.backend.exceptions.ImageFormatNotAllowedException;
import com.cofinprobootcamp.backend.exceptions.ProfileNotFoundException;
import com.cofinprobootcamp.backend.image.dto.*;
import com.cofinprobootcamp.backend.profile.ProfileController;
import com.cofinprobootcamp.backend.profile.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Objects;


@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService imageService;
    private final ProfileRepository profileRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public ImageController(ImageService imageService, ProfileRepository profileRepository,
                           ImageRepository imageRepository) {
        this.imageService = imageService;
        this.profileRepository = profileRepository;
        this.imageRepository = imageRepository;
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
    @PreAuthorize("hasPermission(#id, 'void',@authorityPrefix + 'IMAGES_DELETE_BY_ID')")
    public void deleteImage(@PathVariable String profileId) throws ProfileNotFoundException {
        Image image = profileRepository
                .findFirstByOuterId(profileId)
                .orElseThrow(ProfileNotFoundException::new)
                .getProfilePic();
        image.setData("".getBytes());
        image.setPrefix("none");
        imageRepository.save(image);
    }
}

