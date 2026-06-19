package com.example.adminpanelbackend.repository;

import com.example.adminpanelbackend.model.Regions.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICityRepository extends JpaRepository<City, Long> {

    Optional<City> findByIdAndDeletedFalse(Long id);

    List<City> findAllByDeletedFalse();

    boolean existsByCityNameAndDeletedFalse(String cityName);

    boolean existsByCityNameAndDeletedFalseAndIdNot(String cityName, Long id);
}
