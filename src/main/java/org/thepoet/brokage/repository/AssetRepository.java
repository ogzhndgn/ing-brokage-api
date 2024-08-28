package org.thepoet.brokage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.thepoet.brokage.entity.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    @Query("SELECT a FROM Asset a JOIN FETCH a.customer c WHERE a.id = :assetId")
    Asset getByIdWithCustomer(Long assetId);
}
