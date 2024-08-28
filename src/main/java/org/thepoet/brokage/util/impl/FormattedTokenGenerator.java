package org.thepoet.brokage.util.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.thepoet.brokage.util.spec.UniqueIdGenerator;

@Component
public class FormattedTokenGenerator implements UniqueIdGenerator {
    @Override
    public String generateUniqueId() {
        final String firstPart = RandomStringUtils.randomAlphanumeric(6);
        final String nowInHex = Long.toHexString(System.currentTimeMillis());
        return firstPart + nowInHex;
    }
}
