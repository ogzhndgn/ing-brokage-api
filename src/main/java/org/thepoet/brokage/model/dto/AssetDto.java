package org.thepoet.brokage.model.dto;


import lombok.Builder;

@Builder
public record AssetDto(String name,
                       Long size,
                       Long usableSize) {
}
