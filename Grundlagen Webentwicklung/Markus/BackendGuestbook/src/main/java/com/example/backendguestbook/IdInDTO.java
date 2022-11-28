package com.example.backendguestbook;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class IdInDTO {
    private Long id;

    public IdInDTO() {
    }

    public IdInDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
