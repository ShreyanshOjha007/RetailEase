package com.shreyansh.BillingSoftware.service.impl;

import com.shreyansh.BillingSoftware.entity.OrderEntity;
import com.shreyansh.BillingSoftware.entity.OrderItemEntity;
import com.shreyansh.BillingSoftware.io.PaymentStatus;
import com.shreyansh.BillingSoftware.io.*;
import com.shreyansh.BillingSoftware.repository.OrderEntityRepo;
import com.shreyansh.BillingSoftware.repository.OrderItemEntityRepo;
import com.shreyansh.BillingSoftware.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderEntityRepo orderEntityRepo;

    private final OrderItemEntityRepo orderItemEntityRepo;

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        OrderEntity newOrder = convertToOrderEntity(orderRequest);
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setPaymentStatus(
                newOrder.getPaymentMethod() == PaymentMethod.CASH ?
                        PaymentStatus.COMPLETED
                        : PaymentStatus.PENDING);
        newOrder.setPaymentDetails(paymentDetails);
        List<OrderItemEntity> orderItemEntityList = orderRequest.getCartItems().stream()
                .map(this::convertToOrderItemEntity)
                .collect(Collectors.toList());
        newOrder.setItems(orderItemEntityList);

        newOrder = orderEntityRepo.save(newOrder);
        return convertToOrderResponse(newOrder);

    }

    @Override
    public void deleteOrder(String orderId) {
        OrderEntity existingOrder = orderEntityRepo.findByOrderId(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"order with orderId "+orderId+" doesn't exist"));
        try{
            orderEntityRepo.delete(existingOrder);
        } catch (Exception e){
            throw new RuntimeException("Error while deleting the order" + e.getMessage());
        }
    }

    @Override
    public List<OrderResponse> getLatestOrders() {
        List<OrderResponse> latestOrders = orderEntityRepo.findAllByOrderByCreatedAtDesc()
                .stream().map(this::convertToOrderResponse)
                .collect(Collectors.toList());
        return latestOrders;
    }

    @Override
    public OrderResponse verifyPayment(PaymentVerificationRequest request) {
        OrderEntity existingOrder = orderEntityRepo.findByOrderId(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if(!verifyRazorpaySignature(request.getRazorpaySignature(),
                request.getRazorpayOrderId(),request.getRazorpayPaymentId())){
            throw new RuntimeException("Payment verification failed");
        }
        PaymentDetails paymentDetails = existingOrder.getPaymentDetails();
        paymentDetails.setRazorpaySignature(request.getRazorpaySignature());
        paymentDetails.setRazorpayPaymentId(request.getRazorpayPaymentId());
        paymentDetails.setRazorpayOrderId(request.getRazorpayOrderId());
        paymentDetails.setPaymentStatus(PaymentStatus.COMPLETED);
        System.out.println("before saving existing order" + existingOrder);
        existingOrder.setPaymentDetails(paymentDetails);
        existingOrder = orderEntityRepo.save(existingOrder);
        System.out.println("after saving existing order" + existingOrder);
        return convertToOrderResponse(existingOrder);

    }

    @Override
    public Double sumSalesByDate(LocalDate date) {
        return orderEntityRepo.sumSalesByDate(date);
    }

    @Override
    public Long countByOrderDate(LocalDate date) {
        return orderEntityRepo.countByOrderDate(date);
    }

    @Override
    public List<OrderResponse> findRecentOrders() {
        return orderEntityRepo.findRecentOrders(PageRequest.of(0,5))
                .stream().map(this::convertToOrderResponse)
                .collect(Collectors.toList());
    }

    private boolean verifyRazorpaySignature(String razorpaySignature, String razorpayOrderId, String razorpayPaymentId) {
        return true;
    }

    private OrderItemEntity convertToOrderItemEntity(OrderRequest.OrderItemRequest orderItemRequest) {
        return OrderItemEntity.builder()
                .itemId(orderItemRequest.getItemId())
                .name(orderItemRequest.getName())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity()).build();
    }

    private OrderResponse convertToOrderResponse(OrderEntity newOrder) {
        return OrderResponse.builder()
                .orderId(newOrder.getOrderId())
                .customerName(newOrder.getCustomerName())
                .contactNumber(newOrder.getContactNumber())
                .subTotal(newOrder.getSubTotal())
                .tax(newOrder.getTax())
                .grandTotal(newOrder.getGrandTotal())
                .paymentMethod(newOrder.getPaymentMethod())
                .items(newOrder.getItems().stream().map(this::convertToItemResponse)
                        .collect(Collectors.toList()))
                .paymentDetails(newOrder.getPaymentDetails())
                .createdAt(newOrder.getCreatedAt())
                .build();
    }

    private OrderResponse.OrderItemResponse convertToItemResponse(OrderItemEntity orderItemEntity) {
        return OrderResponse.OrderItemResponse.builder()
                .name(orderItemEntity.getName())
                .price(orderItemEntity.getPrice())
                .itemId(orderItemEntity.getItemId())
                .quantity(orderItemEntity.getQuantity())
                .build();
    }


    public OrderEntity convertToOrderEntity(OrderRequest orderRequest){
        return OrderEntity.builder()
                .customerName(orderRequest.getCustomerName())
                .contactNumber(orderRequest.getContactNumber())
                .subTotal(orderRequest.getSubTotal())
                .tax(orderRequest.getTax())
                .grandTotal(orderRequest.getGrandTotal())
                .paymentMethod(PaymentMethod.valueOf(orderRequest.getPaymentMethod())).build();
    }

}
