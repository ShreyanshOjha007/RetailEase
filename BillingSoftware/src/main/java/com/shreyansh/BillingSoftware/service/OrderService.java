package com.shreyansh.BillingSoftware.service;

import com.shreyansh.BillingSoftware.io.OrderRequest;
import com.shreyansh.BillingSoftware.io.OrderResponse;
import com.shreyansh.BillingSoftware.io.PaymentVerificationRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest orderRequest);

    void deleteOrder(String orderId);

    List<OrderResponse> getLatestOrders();

    OrderResponse verifyPayment(PaymentVerificationRequest request);

    Double sumSalesByDate(LocalDate date);

    Long countByOrderDate(LocalDate date);

    List<OrderResponse>findRecentOrders();
}
