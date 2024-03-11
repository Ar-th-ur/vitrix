package ru.vitrix.service;

import org.springframework.web.multipart.MultipartFile;
import ru.vitrix.entity.ImageEntity;
import ru.vitrix.exception.FileException;

public interface ImageService {

    ImageEntity fromFile(MultipartFile file);
}
