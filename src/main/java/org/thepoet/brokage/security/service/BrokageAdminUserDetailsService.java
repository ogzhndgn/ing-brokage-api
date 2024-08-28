package org.thepoet.brokage.security.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.thepoet.brokage.exception.ApiException;
import org.thepoet.brokage.exception.ErrorCode;
import org.thepoet.brokage.model.dto.ApiUserAuthenticationDto;
import org.thepoet.brokage.security.model.ApiAdminUserDetails;
import org.thepoet.brokage.service.business.BrokageAdminBusinessService;

@Service
@RequiredArgsConstructor
public class BrokageAdminUserDetailsService implements UserDetailsService {

    private final BrokageAdminBusinessService brokageAdminBusinessService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApiUserAuthenticationDto apiUserAuthenticationDto;
        try {
            apiUserAuthenticationDto = brokageAdminBusinessService.getBrokageAdminForAuthentication(username);
        } catch (ApiException e) {
            throw new UsernameNotFoundException(e.getErrorCode().name());
        } catch (Exception e) {
            throw new UsernameNotFoundException(ErrorCode.INVALID_LOGIN.name());
        }

        return new ApiAdminUserDetails(apiUserAuthenticationDto);
    }
}
