package org.thepoet.brokage.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.thepoet.brokage.model.enums.OrderSide;
import org.thepoet.brokage.model.enums.OrderStatus;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderQueryFilter {
    private String orderToken;
    private String customerId;
    private String customerEmail;
    private OrderSide orderSide;
    private OrderStatus orderStatus;

    @Builder.Default
    private String orderBy = "createdDate";

    @Pattern(regexp = "ASC|DESC", message = "INVALID_DIRECTION_ERROR")
    @Builder.Default
    private String sortDirection = "DESC";

    @Builder.Default
    @Range(min = 0, message = "INVALID_REQUEST_BODY")
    private int page = 0;

    @Builder.Default
    @Max(value = 100, message = "INVALID_REQUEST_BODY")
    private int limit = 20;

}