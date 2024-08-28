package org.thepoet.brokage.repository;

import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.thepoet.brokage.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
