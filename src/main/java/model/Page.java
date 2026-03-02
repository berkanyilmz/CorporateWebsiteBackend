package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "Pages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page extends BaseModel {

    @Column(nullable = false)
    private String pageName;

    @Column(nullable = false)
    private byte pageOrder;

    @Column(nullable = false)
    private String pageUrl;

    @Column(nullable = false)
    private String pageContent;

    @Column(nullable = false)
    private String metaTitle;

    @Column(nullable = false)
    private String metaDescription;

    @Column(nullable = false)
    private boolean isActive;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false)
    private Date createdAt;

}
