package org.prd.paymentservice.util;

import org.prd.paymentservice.model.dto.PaymentDto;
import org.prd.paymentservice.model.entity.PaymentEntity;

public class PaymentMapper {

    public static PaymentDto toDto(PaymentEntity payment) {
        return new PaymentDto(
                payment.getOrderNum(),
                payment.getStatus(),
                payment.getTransactionId()
        );
    }
}