package com.upc.unireview.repository;
import com.upc.unireview.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface ImageRepository extends JpaRepository<Image, Long> {
    public Image findImaeById(Long id);
}
