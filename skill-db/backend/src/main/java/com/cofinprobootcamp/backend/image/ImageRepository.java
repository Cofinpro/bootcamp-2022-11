package com.cofinprobootcamp.backend.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAll();

    Optional<Image> findById(Long id);

    Image save(Image image);

    Image saveAndFlush(Image image);

    void deleteById(Long id);
}

