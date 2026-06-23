package com.example.adminpanelbackend.controller;

import com.example.adminpanelbackend.dto.Page.SliderDto;
import com.example.adminpanelbackend.dto.Page.SliderDtoUI;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IRestSliderController {

    ResponseEntity<List<SliderDto>> getAllSliders();

    ResponseEntity<List<SliderDto>> getActiveSliders();

    ResponseEntity<SliderDto> getSliderById(@PathVariable(value = "id") Long id);

    ResponseEntity<Boolean> addSlider(@Valid @RequestBody SliderDtoUI dto);

    ResponseEntity<Boolean> updateSlider(@PathVariable(value = "id") Long id, @Valid @RequestBody SliderDtoUI dto);

    ResponseEntity<Boolean> deleteSlider(@PathVariable(value = "id") Long id);
}
