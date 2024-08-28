package org.thepoet.brokage.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thepoet.brokage.model.dto.OrderCreateRequest;
import org.thepoet.brokage.model.dto.OrderCreateResponse;
import org.thepoet.brokage.model.dto.OrderQueryFilter;
import org.thepoet.brokage.model.dto.OrderQueryResponse;
import org.thepoet.brokage.service.business.OrderBusinessService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderBusinessService businessService;

    @PostMapping("/orders")
    public ResponseEntity<OrderCreateResponse> createOrder(Principal principal, @RequestBody OrderCreateRequest request) {
        OrderCreateResponse response = businessService.createOrder(principal, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/orders/{orderToken}")
    public ResponseEntity<Void> cancelOrder(Principal principal, @PathVariable String orderToken) {
        businessService.cancelOrder(principal, orderToken);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/orders/{orderToken}/match")
    public ResponseEntity<OrderCreateResponse> matchOrder(Principal principal, @PathVariable String orderToken) {
        businessService.matchOrder(principal, orderToken);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/orders")
    public ResponseEntity<OrderQueryResponse> queryOrders(Principal principal,
                                                          @Valid OrderQueryFilter filter) {
        OrderQueryResponse response = businessService.queryOrders(filter);
        return ResponseEntity.ok(response);
    }
}
