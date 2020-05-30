-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

-- Users
insert into users (first_name, last_name, email, password, enabled) values ('Me', 'Aka', 'AkaMe', 'www', true)
insert into users (first_name, last_name, email, password, enabled) values ('Not', 'Me', 'NotMe', 'sss', true)
insert into users (first_name, last_name, email, password, enabled) values ('Not', 'Me', 'kakadu@kaka.com', 'kakadu', true)

-- Roles
insert into role (name) values ('SIMPLE_USER')
insert into role (name) values ('AGENT')
insert into role (name) values ('ADMIN')

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
insert into user_roles (user_id, role_id) values (3, 1)