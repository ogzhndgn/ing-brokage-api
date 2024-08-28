package org.thepoet.brokage.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "assets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asset extends Base {

    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "size", nullable = false, length = 100)
    private Long size;
    @Column(name = "usable_size", nullable = false, length = 100)
    private Long usableSize;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}