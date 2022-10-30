# TODOs
- [ ] upgrade auth code to
  latest: https://github.com/bezkoder/spring-boot-login-example/blob/master/src/main/java/com/bezkoder/spring/login/security/WebSecurityConfig.java
- [ ] add permissions on JWT so Ng AuthorizationGuard can use them?
- [ ] unsign JWT for ease of use?
- [ ] change naming scheme for jpa generated constraints?
- [ ] fit password jpa property to generated password hash constraints + separate it from clear text constraints
- [ ] avoir send password in clear text? how to do that?

## Archives

- [x] Add AttributeConverter for Gender enum => really necessary?
- [x] Use permissions to authorize access on endpoints: hasAuthority, hasPermission, isAuthenticated
- [x] transform all permissions to enums
- [x] init db data: flyway script? => we'll stick to code first generation
    - [x] populate script
    - [x] drop DB on app start:
        - [x] how to generate Bcrypt passwords with different salts each?
        - [x] Hybrid setup? : https://www.baeldung.com/spring-boot-data-sql-and-schema-sql
        - [x] All programmatically generation or hybrid, or just a sql script?
- [x] make sur roles-perms schema suits Spring Security
- [x] create role hierarchy : https://www.baeldung.com/role-and-privilege-for-spring-security-registration
- [x] add unique constraints