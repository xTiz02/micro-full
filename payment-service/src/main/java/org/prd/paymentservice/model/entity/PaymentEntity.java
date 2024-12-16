package org.prd.paymentservice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.prd.paymentservice.util.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "payments")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private UUID orderNum;
    @Column(name = "transaction_id", nullable = false, unique = true)
    private UUID transactionId;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}