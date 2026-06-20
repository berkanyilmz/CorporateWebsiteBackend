package com.example.adminpanelbackend.model.Page;

import com.example.adminpanelbackend.model.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Pages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Page extends BaseModel {

    @Column(nullable = false)
    private String pageName;

    @Column(nullable = false)
    private byte pageOrder;

    @Column(nullable = false)
    private String pageContent;

    @Column(nullable = false)
    private String metaTitle;

    @Column(nullable = false)
    private String metaDescription;

    @Column(nullable = false)
    private boolean isRoot;

    @Column(nullable = false)
    private boolean isActive;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false)
    private Date createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false)
    private Date updatedDate;

    @Column(nullable = false)
    private boolean deleted = false;

    @Column(nullable = false)
    private boolean isSystem = false;

    // SEO
    @Column(nullable = true)
    private String seoTitle;

    @Column(nullable = true)
    private String seoDescription;

    @Column(nullable = true)
    private String seoKeywords;

    //Parent Page
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Page parentPage;

    @OneToMany(mappedBy = "parentPage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Page> childPages;

}
