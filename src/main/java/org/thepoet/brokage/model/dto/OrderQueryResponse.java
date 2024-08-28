package org.thepoet.brokage.model.dto;


import lombok.*;

import java.util.List;

@Builder
public record OrderQueryResponse(
        Long orderCount,
        int pageSize,
        int pageNumber,
        int totalPages,
        List<OrderQueryResultDto> orders) {
}