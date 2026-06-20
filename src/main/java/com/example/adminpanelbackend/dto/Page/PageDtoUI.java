package com.example.adminpanelbackend.dto.Page;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDtoUI {

    @NotBlank(message = "Page name cannot be empty")
    private String pageName;

    @NotNull(message = "Page order is required")
    private Byte pageOrder;

    @NotBlank(message = "Page content cannot be empty")
    private String pageContent;

    @NotBlank(message = "Meta title cannot be empty")
    private String metaTitle;

    @NotBlank(message = "Meta description cannot be empty")
    private String metaDescription;

    @NotNull(message = "Root status is required")
    private Boolean root;

    @NotNull(message = "Active status is required")
    private Boolean active;

    private Boolean system;

    @Size(max = 200, message = "SEO title must be at most 200 characters")
    private String seoTitle;

    @Size(max = 500, message = "SEO description must be at most 500 characters")
    private String seoDescription;

    private String seoKeywords;

    private Long parentPageId;
}
