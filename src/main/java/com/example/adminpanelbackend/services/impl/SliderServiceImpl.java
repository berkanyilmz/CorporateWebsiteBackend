package com.example.adminpanelbackend.services.impl;

import com.example.adminpanelbackend.dto.Page.SliderDto;
import com.example.adminpanelbackend.dto.Page.SliderDtoUI;
import com.example.adminpanelbackend.model.Image.Image;
import com.example.adminpanelbackend.model.Page.Slider;
import com.example.adminpanelbackend.model.Product.Products;
import com.example.adminpanelbackend.repository.IImageRepository;
import com.example.adminpanelbackend.repository.IProductRepository;
import com.example.adminpanelbackend.repository.ISliderRepository;
import com.example.adminpanelbackend.services.ISliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SliderServiceImpl implements ISliderService {

    @Autowired
    private ISliderRepository sliderRepository;

    @Autowired
    private IImageRepository imageRepository;

    @Autowired
    private IProductRepository productRepository;

    @Override
    public ResponseEntity<List<SliderDto>> getAllSliders() {
        List<Slider> sliders = sliderRepository.findAll();
        if (sliders.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<SliderDto> dtos = sliders.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<List<SliderDto>> getActiveSliders() {
        List<Slider> sliders = sliderRepository.findAllByActiveTrueOrderBySortOrderAsc();
        if (sliders.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<SliderDto> dtos = sliders.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<SliderDto> getSliderById(Long id) {
        Optional<Slider> slider = sliderRepository.findById(id);
        if (slider.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapToDto(slider.get()));
    }

    @Override
    public ResponseEntity<Boolean> createSlider(SliderDtoUI dto) {
        Optional<Image> image = imageRepository.findById(dto.getImageId());
        if (image.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }

        Optional<Products> product = productRepository.findByIdAndDeletedFalse(dto.getProductId());
        if (product.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }

        Slider slider = new Slider();
        slider.setImage(image.get());
        slider.setProducts(product.get());
        slider.setTitle(dto.getTitle());
        slider.setSortOrder(dto.getSortOrder());
        slider.setActive(dto.isActive());
        sliderRepository.save(slider);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> updateSlider(Long id, SliderDtoUI dto) {
        Optional<Slider> existing = sliderRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }

        Optional<Image> image = imageRepository.findById(dto.getImageId());
        if (image.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }

        Optional<Products> product = productRepository.findByIdAndDeletedFalse(dto.getProductId());
        if (product.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }

        Slider slider = existing.get();
        slider.setImage(image.get());
        slider.setProducts(product.get());
        slider.setTitle(dto.getTitle());
        slider.setSortOrder(dto.getSortOrder());
        slider.setActive(dto.isActive());
        sliderRepository.save(slider);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> deleteSlider(Long id) {
        if (!sliderRepository.existsById(id)) {
            return ResponseEntity.badRequest().body(false);
        }
        sliderRepository.deleteById(id);
        return ResponseEntity.ok(true);
    }

    private SliderDto mapToDto(Slider slider) {
        SliderDto dto = new SliderDto();
        dto.setId(slider.getId());
        dto.setImageId(slider.getImage().getId());
        dto.setImagePath(slider.getImage().getImagePath());
        dto.setProductId(slider.getProducts().getId());
        dto.setProductName(slider.getProducts().getProductName());
        dto.setTitle(slider.getTitle());
        dto.setSortOrder(slider.getSortOrder());
        dto.setActive(slider.isActive());
        return dto;
    }
}
