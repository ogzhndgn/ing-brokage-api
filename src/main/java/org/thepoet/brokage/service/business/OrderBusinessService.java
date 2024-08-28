package org.thepoet.brokage.service.business;

import jdk.jfr.Frequency;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.thepoet.brokage.entity.Asset;
import org.thepoet.brokage.entity.BrokageAdmin;
import org.thepoet.brokage.entity.Customer;
import org.thepoet.brokage.entity.Order;
import org.thepoet.brokage.exception.ApiException;
import org.thepoet.brokage.exception.ErrorCode;
import org.thepoet.brokage.mapper.OrderMapper;
import org.thepoet.brokage.model.dto.*;
import org.thepoet.brokage.model.enums.OrderSide;
import org.thepoet.brokage.security.model.ApiAdminUserDetails;
import org.thepoet.brokage.service.spec.AssetService;
import org.thepoet.brokage.service.spec.BrokageAdminService;
import org.thepoet.brokage.service.spec.OrderService;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderBusinessService {

    private final BrokageAdminService brokageAdminService;
    private final AssetService assetService;
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    //This method may worked async. The request may be saved to a queue and processed later via different bean which is listening to the queue.
    //Or maybe transactional outbox pattern can be used to save the request to a table and process it later.
    //The request may be saved to a table, 200 status code is returned and json request body is processed by a scheduler job then inform the user via email

    //BrokageAdmin object can be stored in Principal object and via this do not need to go to database to get the user object.
    //For now to prevent this findByEmail method is cached in application memory
    @Transactional
    public OrderCreateResponse createOrder(Principal principal, OrderCreateRequest request) {
        final BrokageAdmin brokageAdmin = this.getBrokageAdminFromPrincipal(principal);
        final Asset asset = this.getAssetWithCustomerById(request.assetId());
        final Customer customer = asset.getCustomer();
        this.validateCustomer(customer, request.customerId());
        this.validateAssetUsableSize(asset, request);
        final OrderDto orderDto = getOrderDto(request, brokageAdmin, asset, customer);
        final Order order = orderService.createOrderFromOrderDto(orderDto);
        final OrderCreateResponse response = orderMapper.mapEntityToResponse(order);
        log.info("Order created by: " + brokageAdmin.getEmail());
        return response;
    }

    public void cancelOrder(Principal principal, String orderToken) {
        final BrokageAdmin brokageAdmin = this.getBrokageAdminFromPrincipal(principal);
        final Order order = orderService.getOrderByOrderToken(orderToken);
        checkOrdersBrokageAdmin(brokageAdmin, order);
        orderService.cancelOrder(order);
        log.info("Order canceled by: " + brokageAdmin.getEmail());
    }


    @Transactional
    public void matchOrder(Principal principal, String orderToken) {
        final BrokageAdmin brokageAdmin = this.getBrokageAdminFromPrincipal(principal);
        final Order order = orderService.getOrderByOrderToken(orderToken);
        checkOrdersBrokageAdmin(brokageAdmin, order);
        orderService.matchOrder(order);
        if (order.getOrderSide().equals(OrderSide.SELL)) {
            assetService.decreaseAssetSize(order.getAsset(), order.getSize());
        }
        if (order.getOrderSide().equals(OrderSide.BUY)) {
            assetService.increaseAssetSize(order.getAsset(), order.getSize());
        }
        log.info("Order matched by: " + brokageAdmin.getEmail());
    }


    private void checkOrdersBrokageAdmin(BrokageAdmin brokageAdmin, Order order) {
        if (!Objects.equals(order.getBrokageAdmin().getId(), brokageAdmin.getId())) {
            throw new ApiException(ErrorCode.INVALID_BROKAGE_ADMIN_FOR_ORDER);
        }
    }

    private static OrderDto getOrderDto(OrderCreateRequest request, BrokageAdmin brokageAdmin, Asset asset, Customer customer) {
        return OrderDto.builder()
                .orderSide(request.side())
                .customer(customer)
                .asset(asset)
                .brokageAdmin(brokageAdmin)
                .size(request.size())
                .price(request.price())
                .build();
    }

    private BrokageAdmin getBrokageAdminFromPrincipal(Principal principal) {
        final UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        final String username = ((ApiAdminUserDetails) token.getPrincipal()).getUsername();
        return brokageAdminService.findByEmail(username);
    }

    private Asset getAssetWithCustomerById(Long assetId) {
        return assetService.getAssetByIdWithCustomer(assetId);
    }

    private void validateCustomer(Customer customer, String customerId) {
        if (!customerId.equals(customer.getCustomerId())) {
            throw new ApiException(ErrorCode.INVALID_CUSTOMER_FOR_ASSET);
        }
    }

    private void validateAssetUsableSize(Asset asset, OrderCreateRequest request) {
        if (request.side().equals(OrderSide.BUY)) {
            return;
        }
        if (asset.getUsableSize() < request.size()) {
            throw new ApiException(ErrorCode.INVALID_ASSET_SIZE_FOR_ORDER);
        }
    }

    public OrderQueryResponse queryOrders(OrderQueryFilter filter) {
        Page<Order> orderPage = orderService.queryOrder(filter);
        List<Order> orders = orderPage.getContent();
        List<OrderQueryResultDto> orderQueryResultDtoList = orderMapper.mapEntityListToQueryResultDtoList(orders);
        int totalPages = orderPage.getTotalPages();
        int pageNumber = orderPage.getNumber();
        int pageSize = orderPage.getSize();
        Long orderCount = orderService.countOrders(filter);

        return OrderQueryResponse.builder()
                .orders(orderQueryResultDtoList)
                .totalPages(totalPages)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .orderCount(orderCount)
                .build();
    }
}
