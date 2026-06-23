package com.example.adminpanelbackend.controller.impl;

import com.example.adminpanelbackend.controller.IRestSliderController;
import com.example.adminpanelbackend.dto.Page.SliderDto;
import com.example.adminpanelbackend.dto.Page.SliderDtoUI;
import com.example.adminpanelbackend.services.ISliderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sliders")
public class RestSliderControllerImpl implements IRestSliderController {

    @Autowired
    private ISliderService sliderService;

    @GetMapping("/all")
    @Override
    public ResponseEntity<List<SliderDto>> getAllSliders() {
        return sliderService.getAllSliders();
    }

    @GetMapping("/active")
    @Override
    public ResponseEntity<List<SliderDto>> getActiveSliders() {
        return sliderService.getActiveSliders();
    }

    @GetMapping("/get/{id}")
    @Override
    public ResponseEntity<SliderDto> getSliderById(@PathVariable(value = "id") Long id) {
        return sliderService.getSliderById(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('Admin', 'Editor')")
    @Override
    public ResponseEntity<Boolean> addSlider(@Valid @RequestBody SliderDtoUI dto) {
        return sliderService.createSlider(dto);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'Editor')")
    @Override
    public ResponseEntity<Boolean> updateSlider(@PathVariable(value = "id") Long id, @Valid @RequestBody SliderDtoUI dto) {
        return sliderService.updateSlider(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('Admin')")
    @Override
    public ResponseEntity<Boolean> deleteSlider(@PathVariable(value = "id") Long id) {
        return sliderService.deleteSlider(id);
    }
}
