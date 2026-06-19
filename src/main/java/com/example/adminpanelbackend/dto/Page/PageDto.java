package com.example.adminpanelbackend.dto.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {

    private Long id;

    private String pageName;

    private byte pageOrder;

    private String pageUrl;

    private String pageContent;

    private String metaTitle;

    private String metaDescription;

    private boolean root;

    private boolean active;

    private Date createdAt;

    private Date updatedDate;

    private boolean deleted;

    private String seoTitle;

    private String seoDescription;

    private String seoKeywords;

    private Long parentPageId;

    private String parentPageName;
}
