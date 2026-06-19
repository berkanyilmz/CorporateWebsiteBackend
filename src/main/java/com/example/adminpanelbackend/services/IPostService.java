package com.example.adminpanelbackend.services;

import com.example.adminpanelbackend.dto.Post.PostDto;
import com.example.adminpanelbackend.dto.Post.PostDtoUI;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPostService {

    ResponseEntity<List<PostDto>> getAllPosts();

    ResponseEntity<PostDto> getPostById(Long id);

    ResponseEntity<Boolean> createPost(PostDtoUI dto);

    ResponseEntity<Boolean> updatePost(Long id, PostDtoUI dto);

    ResponseEntity<Boolean> softDeletePost(Long id);

    ResponseEntity<Boolean> hardDeletePost(Long id);
}
