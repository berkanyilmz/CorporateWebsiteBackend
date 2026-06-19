package com.example.adminpanelbackend.controller.impl;

import com.example.adminpanelbackend.controller.IRestPostController;
import com.example.adminpanelbackend.dto.Post.PostDto;
import com.example.adminpanelbackend.dto.Post.PostDtoUI;
import com.example.adminpanelbackend.services.IPostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class RestPostControllerImpl implements IRestPostController {

    @Autowired
    private IPostService postService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR', 'VIEWER')")
    @Override
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR', 'VIEWER')")
    @Override
    public ResponseEntity<PostDto> getPostById(@PathVariable(value = "id") Long id) {
        return postService.getPostById(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @Override
    public ResponseEntity<Boolean> addPost(@Valid @RequestBody PostDtoUI dto) {
        return postService.createPost(dto);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @Override
    public ResponseEntity<Boolean> updatePost(@PathVariable(value = "id") Long id, @Valid @RequestBody PostDtoUI dto) {
        return postService.updatePost(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<Boolean> softDeletePost(@PathVariable(value = "id") Long id) {
        return postService.softDeletePost(id);
    }

    @DeleteMapping("/delete/{id}/hard")
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<Boolean> hardDeletePost(@PathVariable(value = "id") Long id) {
        return postService.hardDeletePost(id);
    }
}
