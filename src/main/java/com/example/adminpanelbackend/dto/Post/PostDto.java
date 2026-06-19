package com.example.adminpanelbackend.dto.Post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long id;

    private String postTitle;

    private String postContent;

    private String postUrl;

    private boolean active;

    private int viewCount;

    private Date publishedAt;

    private Date createdAt;

    private boolean deleted;

    private Long categoryId;

    private String categoryName;
}
