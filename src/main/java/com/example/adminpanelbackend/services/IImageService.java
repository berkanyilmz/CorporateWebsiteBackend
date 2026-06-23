package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.Image.ImageDto;
import com.example.adminpanelbackend.dto.Image.ImageDtoUI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {

    ResponseEntity<List<ImageDto>> getAllImages();

    ResponseEntity<ImageDto> getImageById(Long id);

    ResponseEntity<Boolean> createImage(ImageDtoUI dto, MultipartFile file);

    ResponseEntity<Boolean> updateImage(Long id, ImageDtoUI dto, MultipartFile file);

    ResponseEntity<Boolean> deleteImage(Long id);
}
