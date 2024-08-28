package org.thepoet.brokage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.thepoet.brokage.entity.BrokageAdmin;

import java.util.List;

@Repository
public interface BrokageAdminRepository extends JpaRepository<BrokageAdmin, Long> {

    List<BrokageAdmin> findAllByEmail(String email);
}
