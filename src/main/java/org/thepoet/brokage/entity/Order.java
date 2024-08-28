package org.thepoet.brokage.entity;

import jakarta.persistence.*;
import lombok.*;
import org.thepoet.brokage.model.enums.OrderSide;
import org.thepoet.brokage.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends Base {
    @Enumerated(EnumType.STRING)
    @Column(name = "order_side", length = 25)
    private OrderSide orderSide;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 25)
    private OrderStatus status;
    @Column(name = "order_token", nullable = false, length = 100, unique = true)
    private String orderToken;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;
    @ManyToOne
    @JoinColumn(name = "brokage_admin_id")
    private BrokageAdmin brokageAdmin;
    @Column(name = "size", nullable = false, length = 100)
    private Long size;
    @Column(name = "price", nullable = false, length = 100)
    private BigDecimal price;
    @Column(name = "matched_date")
    private ZonedDateTime matchedDate;
}
