package org.thepoet.brokage.model.dto;

import lombok.Builder;
import org.thepoet.brokage.model.enums.OrderSide;
import org.thepoet.brokage.model.enums.OrderStatus;

@Builder
public record OrderQueryResultDto(String orderToken,
                                  OrderStatus status,
                                  OrderSide orderSide,
                                  Long size,
                                  CustomerDto customer,
                                  AssetDto asset,
                                  BrokageAdminDto brokageAdmin) {
}