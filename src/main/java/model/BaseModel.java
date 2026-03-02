package model;

import jakarta.persistence.*;
import lombok.Data;

@MappedSuperclass
@Data
public class BaseModel {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

}
