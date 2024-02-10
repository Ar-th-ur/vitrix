package ru.vitrix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vitrix.entity.ImageEntity;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<ImageEntity, UUID> {
}
