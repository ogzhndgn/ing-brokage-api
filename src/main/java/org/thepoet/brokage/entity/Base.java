package org.thepoet.brokage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@SuperBuilder
public abstract class Base implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @CreationTimestamp
    private ZonedDateTime createdDate;

    @Column(name = "last_modified_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @UpdateTimestamp
    private ZonedDateTime lastModifiedDate;
}