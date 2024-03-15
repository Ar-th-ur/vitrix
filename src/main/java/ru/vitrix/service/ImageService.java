package ru.vitrix.service;

import org.springframework.web.multipart.MultipartFile;
import ru.vitrix.entity.ImageEntity;

public interface ImageService {

    ImageEntity fromFile(MultipartFile file);
}
