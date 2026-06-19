package com.example.adminpanelbackend.services.impl;

import com.example.adminpanelbackend.dto.Post.PostDto;
import com.example.adminpanelbackend.dto.Post.PostDtoUI;
import com.example.adminpanelbackend.model.Category.Category;
import com.example.adminpanelbackend.model.Post;
import com.example.adminpanelbackend.repository.ICategoryRepository;
import com.example.adminpanelbackend.repository.IPostRepository;
import com.example.adminpanelbackend.services.IPostService;
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
public class PostServiceImpl implements IPostService {

    @Autowired
    private IPostRepository postRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<Post> posts = postRepository.findAllByDeletedFalse();
        if (posts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<PostDto> dtos = posts.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<PostDto> getPostById(Long id) {
        Optional<Post> post = postRepository.findByIdAndDeletedFalse(id);
        if (post.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapToDto(post.get()));
    }

    @Override
    public ResponseEntity<Boolean> createPost(PostDtoUI dto) {
        Optional<Category> category = categoryRepository.findByIdAndDeletedFalse(dto.getCategoryId());
        if (category.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }

        String postUrl = dto.getPostUrl();
        if (postUrl == null || postUrl.isBlank()) {
            postUrl = generateSlug(dto.getPostTitle());
        } else {
            postUrl = postUrl.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", "-");
        }
        if (postRepository.existsByPostUrlAndDeletedFalse(postUrl)) {
            return ResponseEntity.badRequest().body(false);
        }

        Post post = new Post();
        post.setPostTitle(dto.getPostTitle());
        post.setPostContent(dto.getPostContent());
        post.setPostUrl(postUrl);
        post.setActive(dto.getActive());
        post.setViewCount(0);
        Date now = new Date();
        post.setPublishedAt(now);
        post.setCreatedAt(now);
        post.setDeleted(false);
        post.setCategory(category.get());
        postRepository.save(post);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> updatePost(Long id, PostDtoUI dto) {
        Optional<Post> existing = postRepository.findByIdAndDeletedFalse(id);
        if (existing.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }
        Optional<Category> category = categoryRepository.findByIdAndDeletedFalse(dto.getCategoryId());
        if (category.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }

        Post post = existing.get();
        String postUrl = dto.getPostUrl();
        if (postUrl != null && !postUrl.isBlank()) {
            postUrl = postUrl.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", "-");
            if (!postUrl.equals(post.getPostUrl()) && postRepository.existsByPostUrlAndDeletedFalseAndIdNot(postUrl, id)) {
                return ResponseEntity.badRequest().body(false);
            }
            post.setPostUrl(postUrl);
        }

        post.setPostTitle(dto.getPostTitle());
        post.setPostContent(dto.getPostContent());
        post.setActive(dto.getActive());
        post.setCategory(category.get());
        postRepository.save(post);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> softDeletePost(Long id) {
        Optional<Post> post = postRepository.findByIdAndDeletedFalse(id);
        if (post.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }
        post.get().setDeleted(true);
        postRepository.save(post.get());
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> hardDeletePost(Long id) {
        if (!postRepository.existsById(id)) {
            return ResponseEntity.badRequest().body(false);
        }
        postRepository.deleteById(id);
        return ResponseEntity.ok(true);
    }

    private PostDto mapToDto(Post post) {
        PostDto dto = new PostDto();
        BeanUtils.copyProperties(post, dto);
        dto.setActive(post.isActive());
        dto.setCategoryId(post.getCategory().getId());
        dto.setCategoryName(post.getCategory().getCategoryName());
        return dto;
    }

    private String generateSlug(String input) {
        String base = input.toLowerCase(Locale.ROOT)
                .replaceAll("[ç]", "c")
                .replaceAll("[ğ]", "g")
                .replaceAll("[ı]", "i")
                .replaceAll("[ö]", "o")
                .replaceAll("[ş]", "s")
                .replaceAll("[ü]", "u")
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
        if (base.isBlank()) {
            base = "post";
        }
        String slug = base;
        int counter = 1;
        while (postRepository.existsByPostUrlAndDeletedFalse(slug)) {
            slug = base + "-" + counter++;
        }
        return slug;
    }
}
