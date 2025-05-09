package com.shreyansh.BillingSoftware.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemResponse {

    private String itemId;

    private String name;

    private String categoryName;

    private String categoryId;

    private String description;

    private BigDecimal price;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private String imgUrl;
}
