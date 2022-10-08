# TODOs

- [ ] init db data: flyway script?
    - [ ] populate script
    - [ ] drop DB on app start:
        - [ ] how to generate Bcrypt passwords with different salts each?
        - [ ] Hybrid setup? : https://www.baeldung.com/spring-boot-data-sql-and-schema-sql
        - [ ] All programmatically generation or hybrid, or just a sql script?
- [x] make sur roles-perms schema suits Spring Security
- [ ] Add AttributeConverter for Gender enum and Role name
- [ ] create role hierarchy : https://www.baeldung.com/role-and-privilege-for-spring-security-registration