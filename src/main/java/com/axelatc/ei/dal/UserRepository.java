package com.axelatc.ei.dal;

import com.axelatc.ei.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}