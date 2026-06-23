package com.example.adminpanelbackend.repository;

import com.example.adminpanelbackend.model.Page.Slider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISliderRepository extends JpaRepository<Slider, Long> {

    List<Slider> findAllByActiveTrueOrderBySortOrderAsc();
}
