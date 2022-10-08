package com.axelatc.ei.dal;

import com.axelatc.ei.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByLabel(String label);
}