package com.cofinprobootcamp.backend.image.dto;

import com.cofinprobootcamp.backend.image.Image;

public record ImageOutDTO(Long id) {
    public ImageOutDTO(Image image) {
        this(image.getId());
    }
}