package com.cofinprobootcamp.backend.image;


import com.cofinprobootcamp.backend.exceptions.MailNotSentException;
import com.cofinprobootcamp.backend.exceptions.ProfileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;


@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService imageService;


    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;

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
    @PreAuthorize("hasPermission(#profileId, 'void', @authorityPrefix + 'IMAGES_DELETE_BY_ID')")
    public void resetImage(@PathVariable String profileId)
            throws ProfileNotFoundException, MailNotSentException {
        imageService.resetImage(profileId);
    }
}

