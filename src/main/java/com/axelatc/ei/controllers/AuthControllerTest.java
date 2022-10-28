package com.axelatc.ei.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
class AuthControllerTest {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('DEACTIVATE_OWN_ACCOUNT')")
    public String checkUserPermission() {
        return "User permission: Deactivate own account.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('DEACTIVATE_USER_ACCOUNTS')")
    public String checkAdminPermission() {
        return "Admin permission: Deactivate user accounts";
    }
}