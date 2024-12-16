package org.prd.orderservice.util;

public enum OrderStatus {
    PENDING,//Pendiente, es decir, el cliente esta intentando comprar lo del carrito
    COMPLETED,//Confirmado,es decir, pagado
    CANCELED,//Cancelado, es decir, el cliente ha cancelado la compra(antes de pagar)
}