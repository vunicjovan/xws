-- Cart
insert into cart(user_id) values (3);
insert into cart(user_id) values (5);
insert into cart(user_id) values (7);

-- Cart items
insert into cart_item(advertisment_id, cart_id) values (1, 1);
insert into cart_item(advertisment_id, cart_id) values (2, 1);

-- Advertisements
insert into advertisement (id, owner_id, deleted) values (1, 2, false);
insert into advertisement (id, owner_id, deleted) values (2, 2, false);
insert into advertisement (id, owner_id, deleted) values (3, 2, false);
insert into advertisement (id, owner_id, deleted) values (4, 2, false);

-- Renting requests
insert into renting_request (start_date, end_date, sender_id, status) values ('2020-10-21 14:39:06', '2020-10-21 15:38:06', 3, 0);
insert into renting_request (start_date, end_date, sender_id, status) values ('2020-01-21 14:39:06', '2020-01-23 15:38:06', 3, 2);
--new
insert into renting_request (start_date, end_date, sender_id, status) values ('2020-07-01 14:39:06', '2020-07-07 15:38:06', 5, 2);
insert into renting_request (start_date, end_date, sender_id, status) values ('2020-06-13 14:39:06', '2020-07-07 15:38:06', 7, 2);
insert into renting_request (start_date, end_date, sender_id, status) values ('2020-08-13 14:39:06', '2020-09-07 15:38:06', 3, 0);


insert into renting_request_advertisements (renting_request_id, advertisement_id) values (1, 1);
insert into renting_request_advertisements (renting_request_id, advertisement_id) values (1, 2);
insert into renting_request_advertisements (renting_request_id, advertisement_id) values (2, 1);
insert into renting_request_advertisements (renting_request_id, advertisement_id) values (2, 2);
--new
insert into renting_request_advertisements (renting_request_id, advertisement_id) values (2, 4);
insert into renting_request_advertisements (renting_request_id, advertisement_id) values (3, 4);
insert into renting_request_advertisements (renting_request_id, advertisement_id) values (4, 4);
insert into renting_request_advertisements (renting_request_id, advertisement_id) values (4, 3);
insert into renting_request_advertisements (renting_request_id, advertisement_id) values (5, 3);

-- Renting Intervals
insert into renting_interval (id, start_date, end_date, advertisement_id)
values (1, '2020-01-21 14:39:06', '2020-01-23 15:38:06', 1);
insert into renting_interval (id, start_date, end_date, advertisement_id)
values (2, '2020-01-21 14:39:06', '2020-01-23 15:38:06', 2);
insert into renting_interval (id, start_date, end_date, advertisement_id)
values (3, '2020-01-21 14:39:06', '2020-01-23 15:38:06', 4);
insert into renting_interval (id, start_date, end_date, advertisement_id)
values (4, '2020-07-01 14:39:06', '2020-07-07 15:38:06', 4);
insert into renting_interval (id, start_date, end_date, advertisement_id)
values (5, '2020-06-13 14:39:06', '2020-07-07 15:38:06', 3);
insert into renting_interval (id, start_date, end_date, advertisement_id)
values (6, '2020-06-13 14:39:06', '2020-07-07 15:38:06', 4);

-- Comments
insert into comment (advertisement_id, renting_request_id) values (1, 2);
insert into comment (advertisement_id, renting_request_id) values (2, 2);
insert into comment (advertisement_id, renting_request_id) values (4, 4);
insert into comment (advertisement_id, renting_request_id) values (4, 3);
insert into comment (advertisement_id, renting_request_id) values (4, 2);

-- Renting Reports
insert into renting_report (kilometers_traveled, content, limit_broken, renting_request_id, advertisement_id)
values (10, 'Vehicle damaged on the side', false, 2, 1);
insert into renting_report (kilometers_traveled, content, limit_broken, renting_request_id, advertisement_id)
values (12, 'Where is the money, Lebowski', false, 2, 2);
insert into renting_report (kilometers_traveled, content, limit_broken, renting_request_id, advertisement_id)
values (25, 'Front left tire damaged', false, 2, 4);
