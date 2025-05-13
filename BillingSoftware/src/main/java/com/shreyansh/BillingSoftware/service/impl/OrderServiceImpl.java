package com.shreyansh.BillingSoftware.service.impl;

import com.shreyansh.BillingSoftware.entity.OrderEntity;
import com.shreyansh.BillingSoftware.entity.OrderItemEntity;
import com.shreyansh.BillingSoftware.io.OrderRequest;
import com.shreyansh.BillingSoftware.io.OrderResponse;
import com.shreyansh.BillingSoftware.io.PaymentDetails;
import com.shreyansh.BillingSoftware.io.PaymentMethod;
import com.shreyansh.BillingSoftware.repository.OrderEntityRepo;
import com.shreyansh.BillingSoftware.repository.OrderItemEntityRepo;
import com.shreyansh.BillingSoftware.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
                        PaymentDetails.PaymentStatus.COMPLETED
                        : PaymentDetails.PaymentStatus.PENDING);
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
