package org.thepoet.brokage.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.thepoet.brokage.entity.Order;
import org.thepoet.brokage.exception.ApiException;
import org.thepoet.brokage.exception.ErrorCode;
import org.thepoet.brokage.model.dto.OrderDto;
import org.thepoet.brokage.model.dto.OrderQueryFilter;
import org.thepoet.brokage.model.enums.OrderStatus;
import org.thepoet.brokage.repository.OrderRepository;
import org.thepoet.brokage.repository.specification.OrderSpecifications;
import org.thepoet.brokage.service.spec.OrderService;
import org.thepoet.brokage.util.spec.UniqueIdGenerator;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    @Qualifier("formattedTokenGenerator")
    private final UniqueIdGenerator uniqueIdGenerator;

    public Order createOrderFromOrderDto(OrderDto orderDto) {
        Order order = new Order();
        order.setOrderToken(uniqueIdGenerator.generateUniqueId());
        order.setOrderSide(orderDto.orderSide());
        order.setAsset(orderDto.asset());
        order.setCustomer(orderDto.customer());
        order.setBrokageAdmin(orderDto.brokageAdmin());
        order.setSize(orderDto.size());
        order.setPrice(orderDto.price());
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderByOrderToken(String orderToken) {
        final Order order = orderRepository.findByOrderToken(orderToken);
        if (order == null) {
            throw new ApiException(ErrorCode.ORDER_NOT_FOUND);
        }
        return order;
    }

    @Override
    public void cancelOrder(Order order) {
        if (!order.getStatus().equals(OrderStatus.PENDING)) {
            throw new ApiException(ErrorCode.INVALID_ORDER_STATUS_FOR_CANCEL);
        }
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    public Order matchOrder(Order order) {
        if (!order.getStatus().equals(OrderStatus.PENDING)) {
            throw new ApiException(ErrorCode.INVALID_ORDER_STATUS_FOR_MATCH);
        }
        order.setStatus(OrderStatus.MATCHED);
        order.setMatchedDate(ZonedDateTime.now());
        return orderRepository.save(order);
    }

    @Override
    public Page<Order> queryOrder(OrderQueryFilter filter) {
        Sort.Direction direction = filter.getSortDirection() == null ? Sort.Direction.DESC : Sort.Direction.valueOf(filter.getSortDirection());
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getLimit(), Sort.by(Sort.Order.by(filter.getOrderBy()).with(direction)));
        Specification<Order> orderSpecification = OrderSpecifications.matchesFilter(filter);
        return orderRepository.findAll(orderSpecification, pageable);
    }

    @Override
    public Long countOrders(OrderQueryFilter filter) {
        Specification<Order> orderSpecification = OrderSpecifications.matchesFilter(filter);
        return orderRepository.count(orderSpecification);
    }
}