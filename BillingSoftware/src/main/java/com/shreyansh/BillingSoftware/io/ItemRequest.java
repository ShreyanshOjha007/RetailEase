package com.shreyansh.BillingSoftware.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {

    private String name;

    private String categoryId;

    private String description;

    private BigDecimal price;

}
