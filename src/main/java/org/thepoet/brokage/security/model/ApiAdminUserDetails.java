package org.thepoet.brokage.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.thepoet.brokage.model.dto.ApiUserAuthenticationDto;

import java.util.Collection;
import java.util.List;

public class ApiAdminUserDetails implements UserDetails {

    private final transient ApiUserAuthenticationDto apiUserAuthenticationDto;

    public ApiAdminUserDetails(ApiUserAuthenticationDto apiUserAuthenticationDto) {
        this.apiUserAuthenticationDto = apiUserAuthenticationDto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.apiUserAuthenticationDto.password();
    }

    @Override
    public String getUsername() {
        return this.apiUserAuthenticationDto.email();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.apiUserAuthenticationDto.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.apiUserAuthenticationDto.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.apiUserAuthenticationDto.isActive();
    }

    @Override
    public boolean isEnabled() {
        return this.apiUserAuthenticationDto.isActive();
    }

    public ApiUserAuthenticationDto getApiUserAuthenticationDto() {
        return apiUserAuthenticationDto;
    }
}
