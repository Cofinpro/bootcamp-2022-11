package com.cofinprobootcamp.backend.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image saveImage(byte[] data, String prefix) {
        Image image = new Image();
        image.setData(data);
        image.setPrefix(prefix);
        return imageRepository.save(image);
    }

    public Image getImage(Long id) {
        if (isNull(id)) return null;
        return imageRepository.findById(id).orElse(null);
    }
}
