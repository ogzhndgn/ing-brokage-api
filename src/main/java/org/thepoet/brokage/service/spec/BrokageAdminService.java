package org.thepoet.brokage.service.spec;

import org.thepoet.brokage.entity.BrokageAdmin;

public interface BrokageAdminService {

    BrokageAdmin findByEmail(String email);
}
