package com.shreyansh.BillingSoftware.io;

import com.shreyansh.BillingSoftware.entity.OrderItemEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private String orderId;

    private String customerName;

    private String contactNumber;

    private Double subTotal;

    private Double grandTotal;

    private Double tax;

    private LocalDateTime createdAt;

    private List<OrderResponse.OrderItemResponse> items;

    private PaymentDetails paymentDetails;

    private PaymentMethod paymentMethod;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderItemResponse{

        private String itemId;

        private String name;

        private Double price;

        private Integer quantity;

    }


}
