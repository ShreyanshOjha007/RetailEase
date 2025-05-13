package com.shreyansh.BillingSoftware.controller;

import com.razorpay.RazorpayException;
import com.shreyansh.BillingSoftware.io.PaymentRequest;
import com.shreyansh.BillingSoftware.io.RazorpayOrderResponse;
import com.shreyansh.BillingSoftware.service.OrderService;
import com.shreyansh.BillingSoftware.service.RazorpayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final RazorpayService razorpayService;

    private final OrderService orderService;

    @PostMapping("/create-order")
    public RazorpayOrderResponse createRazorpayOrder(@RequestBody PaymentRequest paymentRequest) throws RazorpayException {
        return razorpayService.createOrder(paymentRequest.getAmount(),paymentRequest.getCurrency());
    }
}
