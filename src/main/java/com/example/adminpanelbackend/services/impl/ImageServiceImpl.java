package com.example.adminpanelbackend.services.impl;

import com.example.adminpanelbackend.dto.Image.ImageDto;
import com.example.adminpanelbackend.dto.Image.ImageDtoUI;
import com.example.adminpanelbackend.model.Image.Image;
import com.example.adminpanelbackend.repository.IImageRepository;
import com.example.adminpanelbackend.services.FileStorageService;
import com.example.adminpanelbackend.services.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements IImageService {

    @Autowired
    private IImageRepository imageRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public ResponseEntity<List<ImageDto>> getAllImages() {
        List<Image> images = imageRepository.findAll();
        if (images.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<ImageDto> dtos = images.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<ImageDto> getImageById(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        if (image.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapToDto(image.get()));
    }

    @Override
    public ResponseEntity<Boolean> createImage(ImageDtoUI dto, MultipartFile file) {
        String imagePath = resolveImagePath(dto, file, null);
        if (imagePath == null || imagePath.isBlank()) {
            return ResponseEntity.badRequest().body(false);
        }

        Image image = new Image();
        image.setImageName(dto.getImageName());
        image.setImagePath(imagePath);
        image.setAltText(dto.getAltText());
        imageRepository.save(image);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> updateImage(Long id, ImageDtoUI dto, MultipartFile file) {
        Optional<Image> existing = imageRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }

        Image image = existing.get();
        String newPath = resolveImagePath(dto, file, image.getImagePath());
        if (newPath != null && !newPath.equals(image.getImagePath())) {
            image.setImagePath(newPath);
        }

        if (dto.getImageName() != null) {
            image.setImageName(dto.getImageName());
        }
        if (dto.getAltText() != null) {
            image.setAltText(dto.getAltText());
        }
        imageRepository.save(image);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> deleteImage(Long id) {
        Optional<Image> existing = imageRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }
        Image image = existing.get();
        fileStorageService.delete(image.getImagePath());
        imageRepository.delete(image);
        return ResponseEntity.ok(true);
    }

    private String resolveImagePath(ImageDtoUI dto, MultipartFile file, String currentPath) {
        if (file != null && !file.isEmpty()) {
            if (currentPath != null && !currentPath.isBlank()) {
                fileStorageService.delete(currentPath);
            }
            return fileStorageService.store(file, "images");
        }
        if (dto.getImagePath() != null && !dto.getImagePath().isBlank()) {
            return dto.getImagePath();
        }
        return currentPath;
    }

    private ImageDto mapToDto(Image image) {
        ImageDto dto = new ImageDto();
        dto.setId(image.getId());
        dto.setImageName(image.getImageName());
        dto.setImagePath(image.getImagePath());
        dto.setAltText(image.getAltText());
        return dto;
    }
}
