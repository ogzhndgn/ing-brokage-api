package org.thepoet.brokage.model.dto;


import org.thepoet.brokage.model.enums.OrderSide;

import java.math.BigDecimal;

public record OrderCreateResponse(
        String orderToken,
        OrderSide orderSide,
        Long size,
        BigDecimal price) {
}