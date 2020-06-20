-- Advertisements
insert into advertisement
(price, kilometers_per_day_limit, collision_damage_waiver, rating, description, location, owner_id)
values (25.00, 240, false, 0, 'Opis 1', 'Teslic', 3);
insert into advertisement
(price, kilometers_per_day_limit, collision_damage_waiver, rating, description, location, owner_id)
values (5.00, 220, true, 3.0, 'Opis 2', 'Kovilj', 3);
insert into advertisement
(price, kilometers_per_day_limit, collision_damage_waiver, rating, description, location, owner_id)
values (26.00, 10, true, 4.2, 'Opis 3', 'Sremska Kamenica', 3);
insert into advertisement
(price, kilometers_per_day_limit, collision_damage_waiver, rating, description, location, owner_id)
values (21.00, -1, false, 5.0, 'Opis 4', 'Mala Mostanica', 3);

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

-- insert into comment (title, content, allowed, user_id, advertisement_id) values ('Ime', 'Prezime', false, 3, 1);
-- insert into comment (title, content, allowed, user_id, advertisement_id) values ('Ime', 'Prezime', false, 3, 2);
-- insert into comment (title, content, allowed, user_id, advertisement_id) values ('Ime', 'Prezime', false, 3, 1);

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
insert into renting_interval (start_date, end_date, advertisement_id) values ('2020-06-06', '2020-06-08', 1);
insert into renting_interval (start_date, end_date, advertisement_id) values ('2020-06-09', '2020-06-12', 1);
insert into renting_interval (start_date, end_date, advertisement_id) values ('2020-06-15', '2020-06-17', 1);
insert into renting_interval (start_date, end_date, advertisement_id) values ('2020-06-10', '2020-06-16', 2);
insert into renting_interval (start_date, end_date, advertisement_id) values ('2020-06-18', '2020-06-21', 2);
insert into renting_interval (start_date, end_date, advertisement_id) values ('2020-06-07', '2020-06-13', 3);
insert into renting_interval (start_date, end_date, advertisement_id) values ('2020-06-17', '2020-06-20', 3);
insert into renting_interval (start_date, end_date, advertisement_id) values ('2020-06-17', '2020-06-20', 4);
insert into renting_interval (start_date, end_date, advertisement_id) values ('2020-06-13', '2020-06-15', 4);
insert into renting_interval (start_date, end_date, advertisement_id) values ('2020-06-25', '2020-06-28', 4);

-- Comments
-- insert into comment (title, content, allowed, user_id, advertisement_id) values ('First comment', 'This is first comment.', true, 3, 1);
-- insert into comment (title, content, allowed, user_id, advertisement_id) values ('Second comment', 'This is second comment.', true, 3, 1);
-- insert into comment (title, content, allowed, user_id, advertisement_id) values ('Third comment', 'This is third comment.', true, 3, 2);
-- insert into comment (title, content, allowed, user_id, advertisement_id) values ('Fourth comment', 'This is fourth comment.', true, 3, 2);
-- insert into comment (title, content, allowed, user_id, advertisement_id) values ('Fifth comment', 'This is fifth comment.', true, 3, 3);
