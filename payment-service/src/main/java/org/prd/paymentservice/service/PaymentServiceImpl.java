package org.prd.paymentservice.service;

import org.prd.paymentservice.model.dto.PaymentDto;
import org.prd.paymentservice.model.dto.PaymentRequest;
import org.prd.paymentservice.model.entity.PaymentEntity;
import org.prd.paymentservice.model.repository.PaymentRepository;
import org.prd.paymentservice.util.PaymentMapper;
import org.prd.paymentservice.util.PaymentStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional
    public PaymentDto processPayment(PaymentRequest paymentRequest) {
        UUID transactionId = UUID.randomUUID();
        PaymentEntity paymentEntity = new PaymentEntity();
                /*.orderNum(paymentRequest.orderId())
                .transactionId(transactionId)
                .price(paymentRequest.amount())
                .build();*/
        paymentEntity.setOrderNum(paymentRequest.orderId());
        paymentEntity.setTransactionId(transactionId);
        paymentEntity.setPrice(paymentRequest.amount());


        //Processing payment
        int random = (int) (Math.random() * 10);
        if(random % 2 == 0){
            paymentEntity.setStatus(PaymentStatus.SUCCESS);
            paymentEntity = paymentRepository.save(paymentEntity);
            //Enviar email de confirmación
            String email = paymentRequest.email();
        }else{
            paymentEntity.setStatus(PaymentStatus.FAILED);
            paymentEntity = paymentRepository.save(paymentEntity);
            //Enviar email de error
            String email = paymentRequest.email();
        }
        return PaymentMapper.toDto(paymentEntity);

    }



}