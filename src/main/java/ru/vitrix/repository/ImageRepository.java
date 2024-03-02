package ru.vitrix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vitrix.entity.ImageEntity;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
}
