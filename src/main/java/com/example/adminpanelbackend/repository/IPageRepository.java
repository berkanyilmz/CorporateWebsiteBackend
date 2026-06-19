package com.example.adminpanelbackend.repository;

import com.example.adminpanelbackend.model.Page.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPageRepository extends JpaRepository<Page, Long> {

    Optional<Page> findByIdAndDeletedFalse(Long id);

    List<Page> findAllByDeletedFalse();

    boolean existsByPageUrlAndDeletedFalse(String pageUrl);

    boolean existsByPageUrlAndDeletedFalseAndIdNot(String pageUrl, Long id);
}
