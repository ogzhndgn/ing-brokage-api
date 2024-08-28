package org.thepoet.brokage.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.thepoet.brokage.model.enums.OrderSide;

import java.math.BigDecimal;


//Create a new order for a given customer, asset, side, size and price
//Side can be BUY or SELL. Customer is a unique id for a customer

@Builder
public record OrderCreateRequest(
        @NotBlank(message = "PARAMETER_REQUIRED") String customerId,
        @NotNull(message = "PARAMETER_REQUIRED") Long assetId,
        @NotBlank(message = "PARAMETER_REQUIRED") OrderSide side,
        @NotBlank(message = "PARAMETER_REQUIRED") Long size,
        @NotBlank(message = "PARAMETER_REQUIRED") BigDecimal price
) {
}
