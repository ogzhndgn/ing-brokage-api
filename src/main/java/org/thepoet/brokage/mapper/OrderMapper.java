package org.thepoet.brokage.mapper;


import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.thepoet.brokage.entity.Order;
import org.thepoet.brokage.model.dto.OrderCreateResponse;
import org.thepoet.brokage.model.dto.OrderQueryResultDto;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {

    OrderCreateResponse mapEntityToResponse(Order order);

    OrderQueryResultDto mapEntityToQueryResultDto(Order order);

    List<OrderQueryResultDto> mapEntityListToQueryResultDtoList(List<Order> orders);
}
