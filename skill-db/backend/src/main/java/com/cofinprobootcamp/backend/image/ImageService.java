package com.cofinprobootcamp.backend.image;

import com.cofinprobootcamp.backend.exceptions.ImageFormatNotAllowedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static java.util.Objects.isNull;

@Service
public class ImageService {
    private static final String PNG="image/png";
    private static final String JPG="image/jpg";
    private static final String JPEG="image/jpeg";
    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image saveImage(byte[] data, String prefix) throws ImageFormatNotAllowedException {
        Image image = new Image();
        image.setData(data);
        if (!(Objects.equals(prefix, PNG) || Objects.equals(prefix, JPG) || Objects.equals(prefix, JPEG))) {
            throw new ImageFormatNotAllowedException();
        }
        image.setPrefix(prefix);
        return imageRepository.save(image);
    }

    public void removeImage(Long id) {
        System.out.println(id);
        imageRepository.deleteById(id);
    }


    public Image getImage(Long id) {
        if (isNull(id)) return null;
        return imageRepository.findById(id).orElse(null);
    }
}
