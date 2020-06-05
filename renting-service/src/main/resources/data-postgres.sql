-- Cart
insert into cart(user_id) values (3);

-- Cart items
insert into cart_item(advertisment_id, cart_id) values (1, 1);
insert into cart_item(advertisment_id, cart_id) values (2, 1);

-- Advertisements

insert into advertisement (id, owner_id) values (1, 1);
insert into advertisement (id, owner_id) values (2, 1);
insert into advertisement (id, owner_id) values (3, 1);
insert into advertisement (id, owner_id) values (4, 1);
