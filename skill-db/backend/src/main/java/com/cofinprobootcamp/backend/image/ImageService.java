package com.cofinprobootcamp.backend.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image saveImage(byte[] data) {
        Image image = new Image();
        image.setData(data);

        return imageRepository.save(image);
    }

    public Image getImage(Long id) {
        try {
            return imageRepository.findById(id).orElseThrow(Exception::new);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
