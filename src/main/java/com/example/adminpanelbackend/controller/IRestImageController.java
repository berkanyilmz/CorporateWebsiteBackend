package com.example.adminpanelbackend.controller;

import com.example.adminpanelbackend.dto.Image.ImageDto;
import com.example.adminpanelbackend.dto.Image.ImageDtoUI;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IRestImageController {

    ResponseEntity<List<ImageDto>> getAllImages();

    ResponseEntity<ImageDto> getImageById(@PathVariable(value = "id") Long id);

    ResponseEntity<Boolean> addImage(@Valid @ModelAttribute ImageDtoUI dto, @RequestParam(value = "file", required = false) MultipartFile file);

    ResponseEntity<Boolean> updateImage(@PathVariable(value = "id") Long id, @Valid @ModelAttribute ImageDtoUI dto, @RequestParam(value = "file", required = false) MultipartFile file);

    ResponseEntity<Boolean> deleteImage(@PathVariable(value = "id") Long id);
}
