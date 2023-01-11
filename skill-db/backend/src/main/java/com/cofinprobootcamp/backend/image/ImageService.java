package com.cofinprobootcamp.backend.image;

import com.cofinprobootcamp.backend.exceptions.ImageFormatNotAllowedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Objects;

import static java.util.Objects.isNull;

@Service
public class ImageService {
    private static final String PNG="image/png";
    private static final String JPG="image/jpg";
    private static final String JPEG="image/jpeg";
    private static final String NOIMAGE="none";
    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image saveImage(String base64Image) throws ImageFormatNotAllowedException {
        if (base64Image==null || base64Image.equals("")) {
            Image image = new Image();
            image.setPrefix("none");
            image.setData("".getBytes());
            return imageRepository.save(image);
        }
        Image image = convertBase64ToImage(base64Image);
        return imageRepository.save(image);
    }
    public Image updateImageIfGiven(String base64Image, Long profilePicId) throws ImageFormatNotAllowedException {
        if (base64Image==null || base64Image.equals("")) {
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
