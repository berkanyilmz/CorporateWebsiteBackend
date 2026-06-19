package com.example.adminpanelbackend.repository;

import com.example.adminpanelbackend.model.Regions.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICountryRepository extends JpaRepository<Country, Long> {

    Optional<Country> findByIdAndDeletedFalse(Long id);

    List<Country> findAllByDeletedFalse();

    boolean existsByCountryNameAndDeletedFalse(String countryName);

    boolean existsByCountryNameAndDeletedFalseAndIdNot(String countryName, Long id);
}
