insert into company (state, city, street, business_number) values ('s', 's', 's', 's')

-- Users
insert into users (first_name, last_name, email, password, enabled) values ('Me', 'Aka', 'AkaMe', 'www', true)
insert into users (first_name, last_name, email, password, enabled) values ('Not', 'Me', 'NotMe', 'sss', true)

-- Roles
insert into role (name) values ('USER')
insert into role (name) values ('NOT_USER')

-- Permissions
insert into permission (name) values ('add')
insert into permission (name) values ('delete')
insert into permission (name) values ('create')


-- Role - permissions
insert into role_permissions (role_id, permission_id) values (1, 1)
insert into role_permissions (role_id, permission_id) values (1, 2)
insert into role_permissions (role_id, permission_id) values (2, 1)
insert into role_permissions (role_id, permission_id) values (2, 3)

-- User - Roles
insert into user_roles (user_id, role_id) values (1, 1)
insert into user_roles (user_id, role_id) values (2, 2)