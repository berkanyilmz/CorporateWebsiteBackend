package com.example.adminpanelbackend.controller.impl;

import com.example.adminpanelbackend.controller.IRestImageController;
import com.example.adminpanelbackend.dto.Image.ImageDto;
import com.example.adminpanelbackend.dto.Image.ImageDtoUI;
import com.example.adminpanelbackend.services.IImageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/images")
public class RestImageControllerImpl implements IRestImageController {

    @Autowired
    private IImageService imageService;

    @GetMapping("/all")
    @Override
    public ResponseEntity<List<ImageDto>> getAllImages() {
        return imageService.getAllImages();
    }

    @GetMapping("/get/{id}")
    @Override
    public ResponseEntity<ImageDto> getImageById(@PathVariable(value = "id") Long id) {
        return imageService.getImageById(id);
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('Admin', 'Editor')")
    @Override
    public ResponseEntity<Boolean> addImage(@Valid @ModelAttribute ImageDtoUI dto, @RequestParam(value = "file", required = false) MultipartFile file) {
        return imageService.createImage(dto, file);
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('Admin', 'Editor')")
    @Override
    public ResponseEntity<Boolean> updateImage(@PathVariable(value = "id") Long id, @Valid @ModelAttribute ImageDtoUI dto, @RequestParam(value = "file", required = false) MultipartFile file) {
        return imageService.updateImage(id, dto, file);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('Admin')")
    @Override
    public ResponseEntity<Boolean> deleteImage(@PathVariable(value = "id") Long id) {
        return imageService.deleteImage(id);
    }
}
