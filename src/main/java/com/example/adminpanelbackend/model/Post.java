package com.example.adminpanelbackend.model;

import com.example.adminpanelbackend.model.Category.Category;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "Posts")
@Data
@EqualsAndHashCode(callSuper = true)
public class Post extends BaseModel {

    @Column(nullable = false)
    private String postTitle;

    @Column(nullable = false)
    private String postContent;

    @Column(nullable = false)
    private String postUrl;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private int viewCount;

    @Column(nullable = false)
    private Date publishedAt;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Category category;

}
