package com.shreyansh.BillingSoftware.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private String customerName;

    private String contactNumber;

    private Double subTotal;

    private Double tax;

    private Double grandTotal;

    private List<OrderItemRequest> cartItems;

    private String paymentMethod;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderItemRequest{

        private String itemId;

        private String name;

        private Double price;

        private Integer quantity;

    }

}
