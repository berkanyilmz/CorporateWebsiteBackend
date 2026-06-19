package com.example.adminpanelbackend.dto.Post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDtoUI {

    @NotBlank(message = "Post title cannot be empty")
    private String postTitle;

    @NotBlank(message = "Post content cannot be empty")
    private String postContent;

    @Size(max = 200, message = "Post URL must be at most 200 characters")
    private String postUrl;

    @NotNull(message = "Active status is required")
    private Boolean active;

    @NotNull(message = "Category ID is required")
    private Long categoryId;
}
