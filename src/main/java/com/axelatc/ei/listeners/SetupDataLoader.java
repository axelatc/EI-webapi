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
import java.util.Set;

// class derived inspired from: https://www.baeldung.com/role-and-privilege-for-spring-security-registration#setup-privileges-and-roles
@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

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

        createAdminRole();
        createUserRole();
        createUsers();
        alreadySetup = true;
    }

    @Transactional
    void createUserRole() {
        Set<Permission> permissions = Set.of(
                permissionRepository.save(new Permission("logout")),
                permissionRepository.save(new Permission("dashboard:read")),
                permissionRepository.save(new Permission("own_account:*")),
                permissionRepository.save(new Permission("own_account:read")),
                permissionRepository.save(new Permission("own_account:edit")),
                permissionRepository.save(new Permission("own_account:deactivate")),
                permissionRepository.save(new Permission("own_measures:*")),
                permissionRepository.save(new Permission("own_measures:new")),
                permissionRepository.save(new Permission("own_measures:read")),
                permissionRepository.save(new Permission("own_measures:edit")),
                permissionRepository.save(new Permission("own_measures:delete"))
        );

        Role roleUserToPersist = Role
                .builder()
                .label("ROLE_USER")
                .description("Rôle de l'utilisateur connecté")
                .permissions(permissions)
                .build();
        roleRepository.save(roleUserToPersist);
    }

    @Transactional
    void createAdminRole() {
        Set<Permission> permissions = Set.of(
                permissionRepository.save(new Permission("user_accounts:*")),
                permissionRepository.save(new Permission("user_accounts:new")),
                permissionRepository.save(new Permission("user_accounts:read")),
                permissionRepository.save(new Permission("user_accounts:edit")),
                permissionRepository.save(new Permission("user_accounts:deactivate")),
                permissionRepository.save(new Permission("roles:*")),
                permissionRepository.save(new Permission("roles:new")),
                permissionRepository.save(new Permission("roles:read")),
                permissionRepository.save(new Permission("roles:edit")),
                permissionRepository.save(new Permission("roles:delete")),
                permissionRepository.save(new Permission("roles:add_permission")),
                permissionRepository.save(new Permission("roles:remove_permission")),
                permissionRepository.save(new Permission("permissions:read")),
                permissionRepository.save(new Permission("measurands:*")),
                permissionRepository.save(new Permission("measurands:new")),
                permissionRepository.save(new Permission("measurands:read")),
                permissionRepository.save(new Permission("measurands:edit")),
                permissionRepository.save(new Permission("measurands:delete"))
        );
        Role roleAdminToPersist = Role
                .builder()
                .label("ROLE_ADMIN")
                .description("Rôle de l'administrateur")
                .permissions(permissions)
                .build();
        roleRepository.save(roleAdminToPersist);
    }

    @Transactional
    void createUsers() {
        Role roleAdmin = roleRepository.findByLabel(RoleEnum.ROLE_ADMIN.toString());
        User admin1 = User.builder()
                .firstName("Bugs")
                .lastName("Bunny")
                .username("admin_bugs")
                .password(passwordEncoder.encode("test1234="))
                .birthdate(LocalDate.now())
                .gender(Gender.MALE)
                .emailAddress("bugsbunny@example.com")
                .creationDateTime(LocalDateTime.now())
                .isActive(true)
                .role(roleAdmin)
                .build();
        userRepository.save(admin1);

        User admin2 = User.builder()
                .firstName("Marvin")
                .lastName("The Martian")
                .username("admin_marvin")
                .password(passwordEncoder.encode("test1234="))
                .birthdate(LocalDate.now())
                .gender(Gender.OTHER)
                .emailAddress("marvinthemartian@example.com")
                .creationDateTime(LocalDateTime.now())
                .isActive(true)
                .role(roleAdmin)
                .build();
        userRepository.save(admin2);

        Role roleUser = roleRepository.findByLabel(RoleEnum.ROLE_USER.toString());
        User user1 = User.builder()
                .firstName("Daffy")
                .lastName("Ducks")
                .username("user_daffy")
                .password(passwordEncoder.encode("test1234="))
                .birthdate(LocalDate.now())
                .gender(Gender.MALE)
                .emailAddress("daffyducks@example.com")
                .creationDateTime(LocalDateTime.now())
                .isActive(true)
                .role(roleUser)
                .build();
        userRepository.save(user1);

        User user2 = User.builder()
                .firstName("Jessica")
                .lastName("Rabbit")
                .username("user_jessica")
                .password(passwordEncoder.encode("test1234="))
                .birthdate(LocalDate.now())
                .gender(Gender.FEMALE)
                .emailAddress("jessicarabbit@example.com")
                .creationDateTime(LocalDateTime.now())
                .isActive(true)
                .role(roleUser)
                .build();
        userRepository.save(user2);
    }

    @Transactional
    Permission createPermissionIfNotFound(String name) {

        Permission permission = permissionRepository.findByLabel(name);
        if (permission == null) {
            permission = Permission.builder().label(name).build();
            permissionRepository.save(permission);
        }
        return permission;
    }

    @Transactional
    Role createRoleIfNotFound(String name, String description, Set<Permission> permissions) {

        Role role = roleRepository.findByLabel(name);
        if (role == null) {
            role = Role
                    .builder()
                    .label(name)
                    .description(description)
                    .permissions(permissions)
                    .build();
            roleRepository.save(role);
        }
        return role;
    }
}
