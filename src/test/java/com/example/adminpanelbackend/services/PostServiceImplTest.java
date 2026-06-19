package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.Post.PostDtoUI;
import com.example.adminpanelbackend.model.Category.Category;
import com.example.adminpanelbackend.model.Post;
import com.example.adminpanelbackend.repository.ICategoryRepository;
import com.example.adminpanelbackend.repository.IPostRepository;
import com.example.adminpanelbackend.services.impl.PostServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private IPostRepository postRepository;

    @Mock
    private ICategoryRepository categoryRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    void createPost_shouldReturnTrue_whenValidInput() {
        PostDtoUI dto = new PostDtoUI();
        dto.setPostTitle("New Post");
        dto.setPostContent("Content");
        dto.setActive(true);
        dto.setCategoryId(1L);

        Category category = new Category();
        category.setId(1L);

        when(categoryRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(category));
        when(postRepository.existsByPostUrlAndDeletedFalse(anyString())).thenReturn(false);

        ResponseEntity<Boolean> response = postService.createPost(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(postRepository).save(any(Post.class));
    }

    @Test
    void createPost_shouldReturnBadRequest_whenCategoryNotFound() {
        PostDtoUI dto = new PostDtoUI();
        dto.setCategoryId(1L);

        when(categoryRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());

        ResponseEntity<Boolean> response = postService.createPost(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody());
    }
}
