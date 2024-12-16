package org.prd.orderservice.model.repository;

import org.prd.orderservice.model.entity.OrderEntity;
import org.prd.orderservice.util.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findByOrderNum(UUID num);

    //Get all orders for User Id
    Page<OrderEntity> findByUserUUID(UUID userUUID, Pageable pageable);

    @Modifying
    @Query("update OrderEntity o set o.status = ?2 where o.orderNum = ?1")
    void updateOrderStatus(UUID orderNum, OrderStatus status);
}