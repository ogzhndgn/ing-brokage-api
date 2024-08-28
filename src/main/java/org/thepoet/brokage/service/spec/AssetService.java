package org.thepoet.brokage.service.spec;

import org.thepoet.brokage.entity.Asset;

public interface AssetService {

    Asset getAssetByIdWithCustomer(Long id);

    void decreaseAssetSize(Asset asset, Long decreaseSize);

    void increaseAssetSize(Asset asset, Long increaseSize);
}
