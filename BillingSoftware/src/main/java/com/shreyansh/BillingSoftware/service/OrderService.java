package com.shreyansh.BillingSoftware.service;

import com.shreyansh.BillingSoftware.io.OrderRequest;
import com.shreyansh.BillingSoftware.io.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest orderRequest);

    void deleteOrder(String orderId);

    List<OrderResponse> getLatestOrders();
}
