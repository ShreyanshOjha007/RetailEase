package com.shreyansh.BillingSoftware.service;


import com.razorpay.RazorpayException;
import com.shreyansh.BillingSoftware.io.RazorpayOrderResponse;

public interface RazorpayService {

    RazorpayOrderResponse createOrder(Double amount, String currency) throws RazorpayException;

}
