package org.thepoet.brokage.mapper;

import org.mapstruct.*;
import org.thepoet.brokage.entity.BrokageAdmin;
import org.thepoet.brokage.model.dto.ApiUserAuthenticationDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BrokageAdminMapper {

    ApiUserAuthenticationDto mapEntityToAuthenticationDto(BrokageAdmin brokageAdmin);
}
