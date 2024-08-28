package org.thepoet.brokage.service.spec;

import org.springframework.data.domain.Page;
import org.thepoet.brokage.entity.Order;
import org.thepoet.brokage.model.dto.OrderDto;
import org.thepoet.brokage.model.dto.OrderQueryFilter;

public interface OrderService {
    Order createOrderFromOrderDto(OrderDto orderDto);

    Order getOrderByOrderToken(String orderToken);

    void cancelOrder(Order order);

    Order matchOrder(Order order);

    Page<Order> queryOrder(OrderQueryFilter filter);

    Long countOrders(OrderQueryFilter filter);
}
