package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.Page.PageDtoUI;
import com.example.adminpanelbackend.model.Page.Page;
import com.example.adminpanelbackend.repository.IPageRepository;
import com.example.adminpanelbackend.services.impl.PageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PageServiceImplTest {

    @Mock
    private IPageRepository pageRepository;

    @InjectMocks
    private PageServiceImpl pageService;

    @Test
    void createPage_shouldReturnTrue_whenValidInput() {
        PageDtoUI dto = new PageDtoUI();
        dto.setPageName("Home");
        dto.setPageOrder((byte) 1);
        dto.setPageUrl("home");
        dto.setPageContent("Home page content");
        dto.setMetaTitle("Home");
        dto.setMetaDescription("Home description");
        dto.setRoot(true);
        dto.setActive(true);

        when(pageRepository.existsByPageUrlAndDeletedFalse("home")).thenReturn(false);

        ResponseEntity<Boolean> response = pageService.createPage(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(pageRepository).save(any(Page.class));
    }

    @Test
    void createPage_shouldReturnBadRequest_whenPageUrlAlreadyExists() {
        PageDtoUI dto = new PageDtoUI();
        dto.setPageName("Home");
        dto.setPageOrder((byte) 1);
        dto.setPageUrl("home");
        dto.setPageContent("Content");
        dto.setMetaTitle("Home");
        dto.setMetaDescription("Description");
        dto.setRoot(true);
        dto.setActive(true);

        when(pageRepository.existsByPageUrlAndDeletedFalse("home")).thenReturn(true);

        ResponseEntity<Boolean> response = pageService.createPage(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody());
    }
}
