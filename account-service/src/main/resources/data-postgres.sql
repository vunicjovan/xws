-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

-- Users
insert into users (first_name, last_name, email, password, enabled, deleted) values ('Richard', 'Hendricks', 'admin@soul.com', '$2y$10$R/LdCKwv38VfUAMuTFH/GOPi3PYHOiYwAUELhptoOHrf0XPMmTdoG', true, false)
insert into users (first_name, last_name, email, password, enabled, deleted) values ('Russ', 'Hanneman', 'agent@soul.com', '$2y$10$RfARh5QRZOaPEiae9l/lW.X5OGp54jupb1ShrnOltBRiE0eImx6eC', true, false)
insert into users (first_name, last_name, email, password, enabled, deleted) values ('Jared', 'Dunn', 'user@soul.com', '$2y$10$onaSB/tLGyTUZ7Rc4dWVFOwqvXHG3oUZfHWgtVQCaWVw/KuyU.sLy', true, false)

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
insert into permission (name) values ('GET_USERS')
insert into permission (name) values ('BLOCK_USER')
insert into permission (name) values ('DELETE_USER')
insert into permission (name) values ('GET_COMMENTS')
insert into permission (name) values ('POST_COMMENT')
insert into permission (name) values ('APPROVE_COMMENT')
insert into permission (name) values ('DELETE_COMMENT')
insert into permission (name) values ('CREATE_ADVERTISEMENT')
insert into permission (name) values ('GET_PRICELIST')
insert into permission (name) values ('CREATE_PRICELIST')
insert into permission (name) values ('GET_RENT_INTERVAL')
insert into permission (name) values ('CREATE_RENT_INTERVAL')
insert into permission (name) values ('CREATE_CATALOG_ITEM')
insert into permission (name) values ('UPDATE_CATALOG_ITEM')
insert into permission (name) values ('DELETE_CATALOG_ITEM')
insert into permission (name) values ('GET_MESSAGES')
insert into permission (name) values ('SEND_MESSAGE')
insert into permission (name) values ('UPDATE_MESSAGE')
insert into permission (name) values ('DELETE_MESSAGE')
insert into permission (name) values ('ADD_CART_ITEM')
insert into permission (name) values ('DELETE_CART_ITEM')
insert into permission (name) values ('GET_CART_ITEMS')
insert into permission (name) values ('CREATE_RENT_REPORT')
insert into permission (name) values ('GET_RENT_REPORT')
insert into permission (name) values ('CREATE_RENT_REQUEST')
insert into permission (name) values ('UPDATE_RENT_REQUEST')
insert into permission (name) values ('GET_RENT_REQUEST')


-- Role - permissions
insert into role_permissions (role_id, permission_id) values (1, 4)
insert into role_permissions (role_id, permission_id) values (1, 5)
insert into role_permissions (role_id, permission_id) values (1, 7)
insert into role_permissions (role_id, permission_id) values (1, 8)
insert into role_permissions (role_id, permission_id) values (1, 9)
insert into role_permissions (role_id, permission_id) values (1, 10)
insert into role_permissions (role_id, permission_id) values (1, 11)
insert into role_permissions (role_id, permission_id) values (1, 12)
insert into role_permissions (role_id, permission_id) values (1, 16)
insert into role_permissions (role_id, permission_id) values (1, 17)
insert into role_permissions (role_id, permission_id) values (1, 18)
insert into role_permissions (role_id, permission_id) values (1, 19)
insert into role_permissions (role_id, permission_id) values (1, 20)
insert into role_permissions (role_id, permission_id) values (1, 21)
insert into role_permissions (role_id, permission_id) values (1, 22)
insert into role_permissions (role_id, permission_id) values (1, 23)
insert into role_permissions (role_id, permission_id) values (1, 24)
insert into role_permissions (role_id, permission_id) values (1, 25)
insert into role_permissions (role_id, permission_id) values (1, 26)
insert into role_permissions (role_id, permission_id) values (1, 27)

insert into role_permissions (role_id, permission_id) values (2, 4)
insert into role_permissions (role_id, permission_id) values (2, 5)
insert into role_permissions (role_id, permission_id) values (2, 7)
insert into role_permissions (role_id, permission_id) values (2, 8)
insert into role_permissions (role_id, permission_id) values (2, 9)
insert into role_permissions (role_id, permission_id) values (2, 10)
insert into role_permissions (role_id, permission_id) values (2, 11)
insert into role_permissions (role_id, permission_id) values (2, 12)
insert into role_permissions (role_id, permission_id) values (2, 16)
insert into role_permissions (role_id, permission_id) values (2, 17)
insert into role_permissions (role_id, permission_id) values (2, 18)
insert into role_permissions (role_id, permission_id) values (2, 19)
insert into role_permissions (role_id, permission_id) values (2, 23)
insert into role_permissions (role_id, permission_id) values (2, 24)
insert into role_permissions (role_id, permission_id) values (2, 27)

insert into role_permissions (role_id, permission_id) values (3, 1)
insert into role_permissions (role_id, permission_id) values (3, 2)
insert into role_permissions (role_id, permission_id) values (3, 3)
insert into role_permissions (role_id, permission_id) values (3, 4)
insert into role_permissions (role_id, permission_id) values (3, 6)
insert into role_permissions (role_id, permission_id) values (3, 7)
insert into role_permissions (role_id, permission_id) values (3, 13)
insert into role_permissions (role_id, permission_id) values (3, 14)
insert into role_permissions (role_id, permission_id) values (3, 15)

-- User - Roles
insert into user_roles (user_id, role_id) values (1, 3)
insert into user_roles (user_id, role_id) values (2, 2)
insert into user_roles (user_id, role_id) values (3, 1)

