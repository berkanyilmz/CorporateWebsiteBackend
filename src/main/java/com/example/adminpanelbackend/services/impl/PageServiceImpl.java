package com.example.adminpanelbackend.services.impl;

import com.example.adminpanelbackend.dto.Page.PageDto;
import com.example.adminpanelbackend.dto.Page.PageDtoUI;
import com.example.adminpanelbackend.model.Page.Page;
import com.example.adminpanelbackend.repository.IPageRepository;
import com.example.adminpanelbackend.services.IPageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PageServiceImpl implements IPageService {

    @Autowired
    private IPageRepository pageRepository;

    @Override
    public ResponseEntity<List<PageDto>> getAllPages() {
        List<Page> pages = pageRepository.findAllByDeletedFalse();
        if (pages.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<PageDto> dtos = pages.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<PageDto> getPageById(Long id) {
        Optional<Page> page = pageRepository.findByIdAndDeletedFalse(id);
        if (page.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapToDto(page.get()));
    }

    @Override
    public ResponseEntity<Boolean> createPage(PageDtoUI dto) {
        String pageUrl = dto.getPageUrl().trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", "-");
        if (pageRepository.existsByPageUrlAndDeletedFalse(pageUrl)) {
            return ResponseEntity.badRequest().body(false);
        }

        Page parentPage = null;
        if (dto.getParentPageId() != null) {
            Optional<Page> parent = pageRepository.findByIdAndDeletedFalse(dto.getParentPageId());
            if (parent.isEmpty()) {
                return ResponseEntity.badRequest().body(false);
            }
            parentPage = parent.get();
        }

        Page page = new Page();
        page.setPageName(dto.getPageName());
        page.setPageOrder(dto.getPageOrder());
        page.setPageUrl(pageUrl);
        page.setPageContent(dto.getPageContent());
        page.setMetaTitle(dto.getMetaTitle());
        page.setMetaDescription(dto.getMetaDescription());
        page.setRoot(dto.getRoot());
        page.setActive(dto.getActive());
        Date now = new Date();
        page.setCreatedAt(now);
        page.setUpdatedDate(now);
        page.setDeleted(false);
        page.setSeoTitle(dto.getSeoTitle());
        page.setSeoDescription(dto.getSeoDescription());
        page.setSeoKeywords(dto.getSeoKeywords());
        page.setParentPage(parentPage);
        pageRepository.save(page);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> updatePage(Long id, PageDtoUI dto) {
        Optional<Page> existing = pageRepository.findByIdAndDeletedFalse(id);
        if (existing.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }

        Page page = existing.get();
        String pageUrl = dto.getPageUrl().trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", "-");
        if (!pageUrl.equals(page.getPageUrl()) && pageRepository.existsByPageUrlAndDeletedFalseAndIdNot(pageUrl, id)) {
            return ResponseEntity.badRequest().body(false);
        }

        Page parentPage = null;
        if (dto.getParentPageId() != null) {
            Optional<Page> parent = pageRepository.findByIdAndDeletedFalse(dto.getParentPageId());
            if (parent.isEmpty()) {
                return ResponseEntity.badRequest().body(false);
            }
            parentPage = parent.get();
        }

        page.setPageName(dto.getPageName());
        page.setPageOrder(dto.getPageOrder());
        page.setPageUrl(pageUrl);
        page.setPageContent(dto.getPageContent());
        page.setMetaTitle(dto.getMetaTitle());
        page.setMetaDescription(dto.getMetaDescription());
        page.setRoot(dto.getRoot());
        page.setActive(dto.getActive());
        page.setUpdatedDate(new Date());
        page.setSeoTitle(dto.getSeoTitle());
        page.setSeoDescription(dto.getSeoDescription());
        page.setSeoKeywords(dto.getSeoKeywords());
        page.setParentPage(parentPage);
        pageRepository.save(page);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> softDeletePage(Long id) {
        Optional<Page> page = pageRepository.findByIdAndDeletedFalse(id);
        if (page.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }
        page.get().setDeleted(true);
        pageRepository.save(page.get());
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> hardDeletePage(Long id) {
        if (!pageRepository.existsById(id)) {
            return ResponseEntity.badRequest().body(false);
        }
        pageRepository.deleteById(id);
        return ResponseEntity.ok(true);
    }

    private PageDto mapToDto(Page page) {
        PageDto dto = new PageDto();
        BeanUtils.copyProperties(page, dto);
        dto.setRoot(page.isRoot());
        dto.setActive(page.isActive());
        if (page.getParentPage() != null) {
            dto.setParentPageId(page.getParentPage().getId());
            dto.setParentPageName(page.getParentPage().getPageName());
        }
        return dto;
    }
}
