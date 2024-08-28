package org.thepoet.brokage.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.thepoet.brokage.entity.BrokageAdmin;
import org.thepoet.brokage.exception.ApiException;
import org.thepoet.brokage.exception.ErrorCode;
import org.thepoet.brokage.repository.BrokageAdminRepository;
import org.thepoet.brokage.service.spec.BrokageAdminService;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class BrokageAdminServiceImpl implements BrokageAdminService {

    private final BrokageAdminRepository repository;

    @Override
    @Cacheable(value = "brokageAdmin", key = "#email")
    public BrokageAdmin findByEmail(String email) {
        List<BrokageAdmin> brokageAdmins = repository.findAllByEmail(email);

        if (brokageAdmins.size() > 1) {
            throw new ApiException(ErrorCode.MULTIPLE_ADMINS_FOUND);
        }

        if (brokageAdmins.size() == 0) {
            throw new ApiException(ErrorCode.ADMIN_NOT_FOUND);
        }
        return brokageAdmins.get(0);
    }
}