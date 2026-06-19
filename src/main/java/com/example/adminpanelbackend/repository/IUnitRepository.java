package com.example.adminpanelbackend.repository;

import com.example.adminpanelbackend.model.Product.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUnitRepository extends JpaRepository<Unit, Long> {

    Optional<Unit> findByUnitName(String unitName);

    Optional<Unit> findByIdAndDeletedFalse(Long id);

    List<Unit> findAllByDeletedFalse();

    boolean existsByUnitNameAndDeletedFalse(String unitName);

    boolean existsByUnitNameAndDeletedFalseAndIdNot(String unitName, Long id);
}
