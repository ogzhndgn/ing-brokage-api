package org.thepoet.brokage.model.dto;

import lombok.Builder;
import org.thepoet.brokage.model.enums.UserStatus;

@Builder
public record ApiUserAuthenticationDto(
        String name,
        String surname,
        String email,
        String password,
        String adminToken,
        UserStatus status
) {
    public boolean isActive() {
        return status == UserStatus.ACTIVE;
    }
}