package com.example.adminpanelbackend.repository;

import com.example.adminpanelbackend.model.Page.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISettingsRepository extends JpaRepository<Settings, Long> {
}
