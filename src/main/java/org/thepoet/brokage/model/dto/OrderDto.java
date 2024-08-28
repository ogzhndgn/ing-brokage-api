package org.thepoet.brokage.model.dto;

import lombok.Builder;
import org.thepoet.brokage.entity.Asset;
import org.thepoet.brokage.entity.BrokageAdmin;
import org.thepoet.brokage.entity.Customer;
import org.thepoet.brokage.model.enums.OrderSide;

import java.math.BigDecimal;

@Builder
public record OrderDto(
        OrderSide orderSide,
        Customer customer,
        Asset asset,
        BrokageAdmin brokageAdmin,
        Long size,
        BigDecimal price) {
}
