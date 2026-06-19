package com.example.adminpanelbackend.controller;

import com.example.adminpanelbackend.dto.Post.PostDto;
import com.example.adminpanelbackend.dto.Post.PostDtoUI;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IRestPostController {

    ResponseEntity<List<PostDto>> getAllPosts();

    ResponseEntity<PostDto> getPostById(@PathVariable(value = "id") Long id);

    ResponseEntity<Boolean> addPost(@Valid @RequestBody PostDtoUI dto);

    ResponseEntity<Boolean> updatePost(@PathVariable(value = "id") Long id, @Valid @RequestBody PostDtoUI dto);

    ResponseEntity<Boolean> softDeletePost(@PathVariable(value = "id") Long id);

    ResponseEntity<Boolean> hardDeletePost(@PathVariable(value = "id") Long id);
}
