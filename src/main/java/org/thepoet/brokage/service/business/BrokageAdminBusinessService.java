package org.thepoet.brokage.service.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thepoet.brokage.entity.BrokageAdmin;
import org.thepoet.brokage.mapper.BrokageAdminMapper;
import org.thepoet.brokage.model.dto.ApiUserAuthenticationDto;
import org.thepoet.brokage.service.spec.BrokageAdminService;

@Component
@RequiredArgsConstructor
public class BrokageAdminBusinessService {

    private final BrokageAdminService brokageAdminService;
    private final BrokageAdminMapper brokageAdminMapper;

    public ApiUserAuthenticationDto getBrokageAdminForAuthentication(String username) {
        BrokageAdmin brokageAdmin = brokageAdminService.findByEmail(username);
        return brokageAdminMapper.mapEntityToAuthenticationDto(brokageAdmin);
    }
}