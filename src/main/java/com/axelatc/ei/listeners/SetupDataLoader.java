package com.axelatc.ei.listeners;

import com.axelatc.ei.dal.PermissionRepository;
import com.axelatc.ei.dal.RoleRepository;
import com.axelatc.ei.dal.UserRepository;
import com.axelatc.ei.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

// class derived inspired from: https://www.baeldung.com/role-and-privilege-for-spring-security-registration#setup-privileges-and-roles
@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private static final List<PermissionEnum> ROLE_USER_PERMISSIONS = List.of(
            PermissionEnum.LOGOUT,
            PermissionEnum.READ_DASHBOARD,
            PermissionEnum.READ_OWN_ACCOUNT,
            PermissionEnum.EDIT_OWN_ACCOUNT,
            PermissionEnum.DEACTIVATE_OWN_ACCOUNT,
            PermissionEnum.NEW_OWN_MEASURES,
            PermissionEnum.READ_OWN_MEASURES,
            PermissionEnum.EDIT_OWN_MEASURES,
            PermissionEnum.DELETE_OWN_MEASURES
    );

    private static final List<PermissionEnum> ROLE_ADMIN_PERMISSIONS = List.of(
            PermissionEnum.NEW_USER_ACCOUNTS,
            PermissionEnum.READ_USER_ACCOUNTS,
            PermissionEnum.EDIT_USER_ACCOUNTS,
            PermissionEnum.DEACTIVATE_USER_ACCOUNTS,
            PermissionEnum.NEW_ROLES,
            PermissionEnum.READ_ROLES,
            PermissionEnum.EDIT_ROLES,
            PermissionEnum.DELETE_ROLES,
            PermissionEnum.ADD_PERMISSION_ROLES,
            PermissionEnum.REMOVE_PERMISSION_ROLES,
            PermissionEnum.READ_PERMISSIONS,
            PermissionEnum.NEW_MEASURANDS,
            PermissionEnum.READ_MEASURANDS,
            PermissionEnum.EDIT_MEASURANDS,
            PermissionEnum.DELETE_MEASURANDS
    );
    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }

        createUserRole();
        createAdminRole();
        createUsers();
        alreadySetup = true;
    }

    @Transactional
    Role createUserRole() {
        Set<Permission> permissions = new HashSet<>(permissionRepository.saveAll(
                ROLE_USER_PERMISSIONS.stream().map(permissionEnum -> new Permission(permissionEnum.toString())).collect(Collectors.toList())));

        Role roleUserToPersist = Role.builder().label("ROLE_USER").description("Rôle de l'utilisateur connecté").permissions(permissions).build();
        return roleRepository.save(roleUserToPersist);
    }

    @Transactional
    void createAdminRole() {
        Set<Permission> adminPermissions = new HashSet<>(permissionRepository.saveAll(
                ROLE_ADMIN_PERMISSIONS.stream()
                        .map(permissionEnum -> new Permission(permissionEnum.toString()))
                        .toList()
        ));
        Optional<Role> userRole = roleRepository.findByLabel(RoleEnum.ROLE_USER.name());
        adminPermissions.addAll(userRole.get().getPermissions());
        Role roleAdminToPersist = Role.builder().label("ROLE_ADMIN").description("Rôle de l'administrateur").permissions(adminPermissions).build();
        roleRepository.save(roleAdminToPersist);
    }

    @Transactional
    void createUsers() {
        Optional<Role> roleAdmin = roleRepository.findByLabel(RoleEnum.ROLE_ADMIN.toString());
        User admin1 = User.builder().firstName("Bugs").lastName("Bunny").username("admin_bugs").password(passwordEncoder.encode("test1234=")).birthdate(LocalDate.now()).gender(Gender.MALE).email("bugsbunny@example.com").creationDateTime(LocalDateTime.now()).isActive(true).role(roleAdmin.get()).build();
        userRepository.save(admin1);

        User admin2 = User.builder().firstName("Marvin").lastName("The Martian").username("admin_marvin").password(passwordEncoder.encode("test1234=")).birthdate(LocalDate.now()).gender(Gender.OTHER).email("marvinthemartian@example.com").creationDateTime(LocalDateTime.now()).isActive(true).role(roleAdmin.get()).build();
        userRepository.save(admin2);

        Optional<Role> roleUser = roleRepository.findByLabel(RoleEnum.ROLE_USER.toString());
        User user1 = User.builder().firstName("Daffy").lastName("Ducks").username("user_daffy").password(passwordEncoder.encode("test1234=")).birthdate(LocalDate.now()).gender(Gender.MALE).email("daffyducks@example.com").creationDateTime(LocalDateTime.now()).isActive(true).role(roleUser.get()).build();
        userRepository.save(user1);

        User user2 = User.builder().firstName("Jessica").lastName("Rabbit").username("user_jessica").password(passwordEncoder.encode("test1234=")).birthdate(LocalDate.now()).gender(Gender.FEMALE).email("jessicarabbit@example.com").creationDateTime(LocalDateTime.now()).isActive(true).role(roleUser.get()).build();
        userRepository.save(user2);
    }

    @Transactional
    Permission createPermissionIfNotFound(String name) {

        Optional<Permission> permission = permissionRepository.findByLabel(name);
        if (permission.isEmpty()) {
            permissionRepository.save(Permission.builder().label(name).build());
        }
        return permission.get();
    }

    @Transactional
    Role createRoleIfNotFound(String name, String description, Set<Permission> permissions) {

        Optional<Role> role = roleRepository.findByLabel(name);
        if (role.isEmpty()) {
            roleRepository.save(Role.builder().label(name).description(description).permissions(permissions).build());
        }
        return role.get();
    }
}
