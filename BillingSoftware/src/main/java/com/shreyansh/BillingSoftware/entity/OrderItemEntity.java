package com.shreyansh.BillingSoftware.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemId;

    private Double price;

    private Integer quantity;

    private String name;


}
