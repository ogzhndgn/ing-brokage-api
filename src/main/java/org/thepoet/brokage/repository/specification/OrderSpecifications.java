package org.thepoet.brokage.repository.specification;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.thepoet.brokage.entity.Asset;
import org.thepoet.brokage.entity.BrokageAdmin;
import org.thepoet.brokage.entity.Customer;
import org.thepoet.brokage.entity.Order;
import org.thepoet.brokage.model.dto.OrderQueryFilter;


public class OrderSpecifications {

    public static Specification<Order> matchesFilter(OrderQueryFilter filter) {
        return (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate p = cb.conjunction();

            if (filter.getOrderToken() != null) {
                p = cb.and(p, cb.equal(root.get("orderToken"), filter.getOrderToken()));
            }

            if (filter.getCustomerId() != null) {
                Join<Order, Customer> customerJoin = root.join("customer", JoinType.LEFT);
                p = cb.and(p, cb.equal(customerJoin.get("customerId"), filter.getCustomerId()));
            }

            if (filter.getCustomerEmail() != null) {
                Join<Order, Customer> customerJoin = root.join("customer", JoinType.LEFT);
                p = cb.and(p, cb.equal(customerJoin.get("email"), filter.getCustomerEmail())); //TODO: LIKE can be used also
            }

            if (filter.getOrderSide() != null) {
                p = cb.and(p, cb.equal(root.get("orderSide"), filter.getOrderSide()));
            }

            if (filter.getOrderStatus() != null) {
                p = cb.and(p, cb.equal(root.get("status"), filter.getOrderStatus()));
            }

            Join<Order, Asset> assetJoin = root.join("asset", JoinType.LEFT);
            Join<Order, BrokageAdmin> brokageAdminJoin = root.join("brokageAdmin", JoinType.LEFT);
            return p;
        };
    }
}
