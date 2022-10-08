package com.axelatc.ei.dal;

import com.axelatc.ei.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByLabel(String label);
}