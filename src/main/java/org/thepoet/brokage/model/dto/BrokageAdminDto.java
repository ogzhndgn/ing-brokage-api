package org.thepoet.brokage.model.dto;

import lombok.Builder;
import org.thepoet.brokage.model.enums.UserStatus;

@Builder
public record BrokageAdminDto(String email,
                              String name,
                              String surname,
                              UserStatus status) {
}
