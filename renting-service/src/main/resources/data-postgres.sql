-- Cart
insert into cart(user_id) values (3);
insert into cart(user_id) values (5);
insert into cart(user_id) values (7);

-- Cart items
insert into cart_item(advertisment_id, cart_id) values (1, 1);
insert into cart_item(advertisment_id, cart_id) values (2, 1);

-- Advertisements
insert into advertisement (id, owner_id) values (1, 2);
insert into advertisement (id, owner_id) values (2, 2);
insert into advertisement (id, owner_id) values (3, 2);
insert into advertisement (id, owner_id) values (4, 2);

-- Renting requests
insert into renting_request (start_date, end_date, sender_id, status) values ('2020-10-21 14:39:06', '2020-10-21 15:38:06', 3, 0);
insert into renting_request (start_date, end_date, sender_id, status) values ('2020-01-21 14:39:06', '2020-01-23 15:38:06', 3, 2);
--novi
insert into renting_request (start_date, end_date, sender_id, status) values ('2020-07-19 14:39:06', '2020-07-25 15:38:06', 5, 0);
insert into renting_request (start_date, end_date, sender_id, status) values ('2020-11-13 14:39:06', '2020-11-09 15:38:06', 7, 0);

insert into renting_request_advertisements (renting_request_id, advertisement_id) values (1, 1);
insert into renting_request_advertisements (renting_request_id, advertisement_id) values (1, 2);
insert into renting_request_advertisements (renting_request_id, advertisement_id) values (2, 1);
insert into renting_request_advertisements (renting_request_id, advertisement_id) values (2, 2);
--novi
insert into renting_request_advertisements (renting_request_id, advertisement_id) values (3, 4);
insert into renting_request_advertisements (renting_request_id, advertisement_id) values (4, 3);

-- Comments
insert into comment (advertisement_id, renting_request_id) values (1, 2);
-- insert into comment (advertisement_id, renting_request_id) values (1, 1);
insert into comment (advertisement_id, renting_request_id) values (2, 2);
-- insert into comment (advertisement_id, renting_request_id) values (2, 2);
-- insert into comment (advertisement_id, renting_request_id) values (3);