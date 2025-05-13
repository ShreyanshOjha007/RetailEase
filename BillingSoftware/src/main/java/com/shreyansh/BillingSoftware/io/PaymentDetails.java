package com.shreyansh.BillingSoftware.io;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetails {

    private String razorpayOrderId;

    private String razorpaySignature;

    private String razorpayPaymentId;

    private PaymentStatus paymentStatus;

    public enum PaymentStatus{
        COMPLETED,PENDING,FAILED
    }


}
