package com.nht.service;

import com.nht.Model.Order;
import com.nht.respone.PaymentResponse;
import com.stripe.exception.StripeException;

public interface PaymentService {

    public PaymentResponse createPaymentLink (Order order) throws StripeException;

}
