package org.prd.orderservice.util;

import org.prd.orderservice.model.dto.BookDto;
import org.prd.orderservice.model.dto.OrderDto;
import org.prd.orderservice.model.dto.OrderRequest;
import org.prd.orderservice.model.entity.OrderEntity;
import org.prd.orderservice.model.entity.OrderItem;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDto toDto(OrderEntity order) {
        return new OrderDto(
                order.getOrderNum(),
                order.getUserUUID(),
                order.getTotal(),
                order.getStatus(),
                order.getItems().stream()
                        .map(OrderMapper::toBookDto)
                        .collect(Collectors.toList())
        );
    }


    public static BookDto toBookDto(OrderItem item) {
        return new BookDto(
                item.getCode(),
                item.getName(),
                item.getPrice()
        );
    }


    public static OrderItem toOrderItem(BookDto bookDto) {
        return OrderItem.builder()
                .code(bookDto.code())
                .name(bookDto.name())
                .price(bookDto.price())
                .build();
    }
}