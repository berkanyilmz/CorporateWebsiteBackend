package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.Page.SliderDto;
import com.example.adminpanelbackend.dto.Page.SliderDtoUI;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ISliderService {

    ResponseEntity<List<SliderDto>> getAllSliders();

    ResponseEntity<List<SliderDto>> getActiveSliders();

    ResponseEntity<SliderDto> getSliderById(Long id);

    ResponseEntity<Boolean> createSlider(SliderDtoUI dto);

    ResponseEntity<Boolean> updateSlider(Long id, SliderDtoUI dto);

    ResponseEntity<Boolean> deleteSlider(Long id);
}
