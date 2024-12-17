package org.prd.catalogservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "books")
public class BookEntity {
    @MongoId
    private String id;
    private String name;
    private String description;
    private String code;
    private String imgUri;
    private boolean active;
    private BigDecimal price;
}