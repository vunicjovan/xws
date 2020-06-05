-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

-- Users
insert into users (first_name, last_name, email, password, enabled) values ('Richard', 'Hendricks', 'admin@soul.com', '$2a$10$R4KtI.gPRqYv3C2YAI.Fv.lDGjQMxGJPnzyqOcTKw23bKVkVguAVS', true)
insert into users (first_name, last_name, email, password, enabled) values ('Russ', 'Hanneman', 'agent@soul.com', '$2a$10$br1I6SY4.QlAabOd3juw5.16R7eL2olnU8vl9QiMIB2.L8sZIJzo2', true)
insert into users (first_name, last_name, email, password, enabled) values ('Jared', 'Dunn', 'user@soul.com', '$2a$10$CUWHTtIHSnEfgQE2ysgeIuAgtBZrBt9IKDF6dCLk9ri4VRvbUlUMm', true)

-- Simple user
insert into simple_user (blocked, number_of_ads, number_of_cancelations, user_id) values (false, 0, 0, 3)

-- Agent
insert into agent (user_id) values (2)

-- Administrator
insert into administrator (user_id) values (1)

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
insert into role_permissions (role_id, permission_id) values (3, 1)
insert into role_permissions (role_id, permission_id) values (3, 2)

-- User - Roles
insert into user_roles (user_id, role_id) values (1, 3)
insert into user_roles (user_id, role_id) values (2, 2)
insert into user_roles (user_id, role_id) values (3, 1)