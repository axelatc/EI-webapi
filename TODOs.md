# TODOs

- [x] init db data: flyway script? => we'll stick to code first generation
    - [ ] populate script
    - [ ] drop DB on app start:
        - [ ] how to generate Bcrypt passwords with different salts each?
        - [ ] Hybrid setup? : https://www.baeldung.com/spring-boot-data-sql-and-schema-sql
        - [ ] All programmatically generation or hybrid, or just a sql script?
- [x] make sur roles-perms schema suits Spring Security
- [ ] Add AttributeConverter for Gender enum and Role name
- [x] create role hierarchy : https://www.baeldung.com/role-and-privilege-for-spring-security-registration
- [x] add unique constraints