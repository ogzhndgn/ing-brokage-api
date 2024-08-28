package org.thepoet.brokage.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Customer extends Base {

    @Column(name = "customer_id", nullable = false, length = 100)
    private String customerId;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    @Builder.Default
    private Set<Asset> assets = new HashSet<>();

}