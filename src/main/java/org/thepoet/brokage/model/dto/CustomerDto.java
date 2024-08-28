package org.thepoet.brokage.model.dto;


import lombok.Builder;

@Builder
public record CustomerDto(String customerId,
                          String name,
                          String email) {
}
