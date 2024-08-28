package org.thepoet.brokage.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thepoet.brokage.entity.Asset;
import org.thepoet.brokage.exception.ApiException;
import org.thepoet.brokage.exception.ErrorCode;
import org.thepoet.brokage.repository.AssetRepository;
import org.thepoet.brokage.service.spec.AssetService;

@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

    private final AssetRepository repository;

    @Override
    public Asset getAssetByIdWithCustomer(Long id) {
        Asset asset = repository.getByIdWithCustomer(id);
        if (asset == null) {
            throw new ApiException(ErrorCode.ASSET_NOT_FOUND);
        }
        return asset;
    }

    @Override
    public void decreaseAssetSize(Asset asset, Long decreaseSize) {
        if (asset.getUsableSize() < decreaseSize) {
            throw new ApiException(ErrorCode.INVALID_ASSET_SIZE_FOR_ORDER);
        }
        asset.setUsableSize(asset.getUsableSize() - decreaseSize);
        repository.save(asset);
    }

    @Override
    public void increaseAssetSize(Asset asset, Long increaseSize) {
        asset.setSize(asset.getSize() + increaseSize);
        asset.setUsableSize(asset.getUsableSize() + increaseSize);
        repository.save(asset);
    }
}