package org.prd.orderservice.util;

import org.prd.orderservice.model.dto.ItemDto;
import org.prd.orderservice.model.dto.OrderDto;
import org.prd.orderservice.model.entity.OrderEntity;
import org.prd.orderservice.model.entity.OrderItem;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDto toDto(OrderEntity order) {
        Set<ItemDto> items = order.getItems().stream()
                .map(OrderMapper::toItemDto)
                .collect(Collectors.toSet());
        BigDecimal total = items.stream()
                .map(ItemDto::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new OrderDto(
                order.getUserUUID(),
                items,
                total
        );
    }


    public static ItemDto toItemDto(OrderItem item) {
        return new ItemDto(
                item.getCode(),
                item.getName(),
                item.getPrice()
        );
    }

    public static OrderItem toItemEntity(ItemDto itemDto) {
        return OrderItem.builder()
                .code(itemDto.code())
                .name(itemDto.name())
                .price(itemDto.price())
                .build();
    }

    public static OrderEntity toEntity(OrderDto orderDto) {
        Set<OrderItem> items = orderDto.items().stream()
                .map(OrderMapper::toItemEntity)
                .collect(Collectors.toSet());
        BigDecimal total = items.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return OrderEntity.builder()
                .userUUID(orderDto.userid())
                .items(items)
                .total(total)
                .build();
    }
}