package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.Page.PageDto;
import com.example.adminpanelbackend.dto.Page.PageDtoUI;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPageService {

    ResponseEntity<List<PageDto>> getAllPages();

    ResponseEntity<PageDto> getPageById(Long id);

    ResponseEntity<Boolean> createPage(PageDtoUI dto);

    ResponseEntity<Boolean> updatePage(Long id, PageDtoUI dto);

    ResponseEntity<Boolean> softDeletePage(Long id);

    ResponseEntity<Boolean> hardDeletePage(Long id);
}
