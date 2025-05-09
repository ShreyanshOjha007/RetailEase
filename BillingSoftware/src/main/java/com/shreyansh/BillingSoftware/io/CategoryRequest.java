package com.shreyansh.BillingSoftware.io;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CategoryRequest {

    private String name;

    private String description;

    private String bgColor;
}
