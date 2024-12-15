package org.prd.catalogservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class BookEntity {
    @Id
    private Long id;
    private String name;
    private String description;
    private String code;
    private String imgUri;
    private boolean active;
    private BigDecimal price;
}