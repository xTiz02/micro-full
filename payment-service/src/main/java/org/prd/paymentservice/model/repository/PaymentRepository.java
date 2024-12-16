package org.prd.paymentservice.model.repository;

import org.prd.paymentservice.model.entity.PaymentEntity;
import org.prd.paymentservice.util.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    PaymentEntity findByOrderNum(UUID orderNum);
    PaymentEntity findByTransactionId(UUID transactionId);
    Page<PaymentEntity> findByStatus(PaymentStatus status, Pageable pageable);
}