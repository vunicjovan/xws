-- Price List
insert into price_list(discount, owner_id) values ( 0.69, 2);

-- Price List Items
insert into price_list_item(cdw_price, daily_price, debt_price, price_list_id)
values (69, 69, 69, 1);

-- Advertisements
insert into advertisement
(price, kilometers_per_day_limit, collision_damage_waiver, rating, description, location, owner_id, deleted, price_list_item_id)
values (69.00, 240, false, 0, 'Opis 1', 'Teslic', 2, false, 1);
insert into advertisement
(price, kilometers_per_day_limit, collision_damage_waiver, rating, description, location, owner_id, deleted, price_list_item_id)
values (69.00, 220, true, 3.0, 'Opis 2', 'Kovilj', 2, false, 1);
insert into advertisement
(price, kilometers_per_day_limit, collision_damage_waiver, rating, description, location, owner_id, deleted, price_list_item_id)
values (69.00, 10, true, 4.2, 'Opis 3', 'Sremska Kamenica', 2, false, 1);
insert into advertisement
(price, kilometers_per_day_limit, collision_damage_waiver, rating, description, location, owner_id, deleted, price_list_item_id)
values (69.00, -1, false, 5.0, 'Opis 4', 'Mala Mostanica', 2, false, 1);

-- Vehicles
insert into vehicle
(kilometers_traveled, child_seat_number, has_android, fuel_type_id, gearbox_type_id, vehicle_class_id, model_id, advertisement_id)
values (15000, 2, true, 1, 1, 1, 1, 1);
insert into vehicle
(kilometers_traveled, child_seat_number, has_android, fuel_type_id, gearbox_type_id, vehicle_class_id, model_id, advertisement_id)
values (2200, 1, false, 1, 2, 1, 2, 2);
insert into vehicle
(kilometers_traveled, child_seat_number, has_android, fuel_type_id, gearbox_type_id, vehicle_class_id, model_id, advertisement_id)
values (5000, 2, true, 2, 1, 3, 1, 3);
insert into vehicle
(kilometers_traveled, child_seat_number, has_android, fuel_type_id, gearbox_type_id, vehicle_class_id, model_id, advertisement_id)
values (1000, 1, true, 3, 2, 3, 4, 4);

-- Photos
insert into photo (path, advertisement_id) values ('logan1.jpg', 1);
insert into photo (path, advertisement_id) values ('logan2.jpg', 1);
insert into photo (path, advertisement_id) values ('logan3.jpeg', 1);
insert into photo (path, advertisement_id) values ('logan4.jpg', 1);
insert into photo (path, advertisement_id) values ('logan1.jpg', 2);
insert into photo (path, advertisement_id) values ('logan2.jpg', 2);
insert into photo (path, advertisement_id) values ('logan3.jpeg', 2);
insert into photo (path, advertisement_id) values ('logan4.jpg', 2);
insert into photo (path, advertisement_id) values ('logan1.jpg', 3);
insert into photo (path, advertisement_id) values ('logan2.jpg', 3);
insert into photo (path, advertisement_id) values ('logan3.jpeg', 3);
insert into photo (path, advertisement_id) values ('logan4.jpg', 3);
insert into photo (path, advertisement_id) values ('logan1.jpg', 4);
insert into photo (path, advertisement_id) values ('logan2.jpg', 4);
insert into photo (path, advertisement_id) values ('logan3.jpeg', 4);
insert into photo (path, advertisement_id) values ('logan4.jpg', 4);

-- Renting Intervals
insert into renting_interval (start_date, end_date, advertisement_id)
values ('2020-01-21 14:39:06', '2020-01-23 15:38:06', 1);
insert into renting_interval (start_date, end_date, advertisement_id)
values ('2020-01-21 14:39:06', '2020-01-23 15:38:06', 2);
insert into renting_interval (start_date, end_date, advertisement_id)
values ('2020-01-21 14:39:06', '2020-01-23 15:38:06', 4);
insert into renting_interval (start_date, end_date, advertisement_id)
values ('2020-07-01 14:39:06', '2020-07-07 15:38:06', 4);
insert into renting_interval (start_date, end_date, advertisement_id)
values ('2020-06-13 14:39:06', '2020-07-07 15:38:06', 3);
insert into renting_interval (start_date, end_date, advertisement_id)
values ('2020-06-13 14:39:06', '2020-07-07 15:38:06', 4);

-- Comments
insert into comment (title, content, allowed, user_id, advertisement_id) values ('First comment', 'This is first comment.', true, 3, 1);
insert into comment (title, content, allowed, user_id, advertisement_id) values ('Third comment', 'This is third comment.', true, 3, 2);
insert into comment (title, content, allowed, user_id, advertisement_id) values ('Sixth comment', 'This is sixth comment.', false, 3, 4);
insert into comment (title, content, allowed, user_id, advertisement_id) values ('Seventh comment', 'This is seventh comment.', false, 5, 4);
insert into comment (title, content, allowed, user_id, advertisement_id) values ('Eight comment', 'This is eighth comment.', false, 7, 4);

-- Android location
insert into android_location (token, latitude, longitude, vehicle_id) values ('789ae3406905658fe37daa6f834842e8', 0.0, 0.0, 1);
