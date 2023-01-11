package com.cofinprobootcamp.backend.image;

import com.cofinprobootcamp.backend.image.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;


@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public ImageOutDTO saveImage(@RequestBody ImageInDTO base64Image) {
        String prefix = base64Image.file().split("[,;:]")[1];
        byte[] imageData = Base64.getDecoder()
                .decode(base64Image.file().split(",")[1]);

        Image image = imageService.saveImage(imageData, prefix);
        return new ImageOutDTO(image);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN', 'SCOPE_ROLE_USER', 'SCOPE_ROLE_HR')")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Image image = imageService.getImage(id);
        byte[] imageData = image.getData();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type",image.getPrefix());
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }
}

