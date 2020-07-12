-- Fuel Types
insert into fuel_type (fuel_type_id, name, deleted) values (1, 'Gasoline', false);
insert into fuel_type (fuel_type_id, name, deleted) values (2, 'Diesel', false);
insert into fuel_type (fuel_type_id, name, deleted) values (3, 'Liquified Petroleum', false);
insert into fuel_type (fuel_type_id, name, deleted) values (4, 'Natural Gas', false);
insert into fuel_type (fuel_type_id, name, deleted) values (5, 'Ethanol', false);
insert into fuel_type (fuel_type_id, name, deleted) values (6, 'Hydrogen', false);
insert into fuel_type (fuel_type_id, name, deleted) values (7, 'Bio Diesel', false);
insert into fuel_type (fuel_type_id, name, deleted) values (8, 'Electricity', false);

-- Gearbox Types
insert into gearbox_type (gearbox_type_id, name, deleted) values (1, 'Automatic', false);
insert into gearbox_type (gearbox_type_id, name, deleted) values (2, 'Manual', false);
insert into gearbox_type (gearbox_type_id, name, deleted) values (3, 'Semi-Automatic', false);
insert into gearbox_type (gearbox_type_id, name, deleted) values (4, 'Continously Variable', false);
insert into gearbox_type (gearbox_type_id, name, deleted) values (5, 'Dual Clutch', false);
insert into gearbox_type (gearbox_type_id, name, deleted) values (6, 'Direct Shift', false);

-- Vehicle Brands
insert into brand (id, deleted, name) values (1, false, 'BMW'); -- id = 1
insert into brand (id, deleted, name) values (2, false, 'Audi'); -- id = 2
insert into brand (id, deleted, name) values (3, false, 'Mercedes Benz'); -- id = 3
insert into brand (id, deleted, name) values (4, false, 'Mazda'); -- id = 4
insert into brand (id, deleted, name) values (5, false, 'Toyota'); -- id = 5
insert into brand (id, deleted, name) values (6, false, 'Honda'); -- id = 6
insert into brand (id, deleted, name) values (7, false, 'Fiat'); -- id = 7
insert into brand (id, deleted, name) values (8, false, 'Ford'); -- id = 8
insert into brand (id, deleted, name) values (9, false, 'Volkswagen'); -- id = 9
insert into brand (id, deleted, name) values (10, false, 'Dacia'); -- id = 10

-- Vehicle Models
insert into model (model_id, name, brand_id, deleted) values (1, 'Series 3', 1, false);
insert into model (model_id, name, brand_id, deleted) values (2, 'Series 5', 1, false);
insert into model (model_id, name, brand_id, deleted) values (3, 'X3', 1, false);
insert into model (model_id, name, brand_id, deleted) values (4, 'X5', 1, false);
insert into model (model_id, name, brand_id, deleted) values (5, 'A4', 2, false);
insert into model (model_id, name, brand_id, deleted) values (6, 'A6', 2, false);
insert into model (model_id, name, brand_id, deleted) values (7, 'Q5', 2, false);
insert into model (model_id, name, brand_id, deleted) values (8, 'Q7', 2, false);
insert into model (model_id, name, brand_id, deleted) values (9, 'Class C', 3, false);
insert into model (model_id, name, brand_id, deleted) values (10, 'Class E', 3, false);
insert into model (model_id, name, brand_id, deleted) values (11, 'GLC', 3, false);
insert into model (model_id, name, brand_id, deleted) values (12, 'GLA', 3, false);
insert into model (model_id, name, brand_id, deleted) values (13, '3', 4, false);
insert into model (model_id, name, brand_id, deleted) values (14, '6', 4, false);
insert into model (model_id, name, brand_id, deleted) values (15, 'CX3', 4, false);
insert into model (model_id, name, brand_id, deleted) values (16, 'CX5', 4, false);
insert into model (model_id, name, brand_id, deleted) values (17, 'Auirs', 5, false);
insert into model (model_id, name, brand_id, deleted) values (18, 'Yaris', 5, false);
insert into model (model_id, name, brand_id, deleted) values (19, 'CHR', 5, false);
insert into model (model_id, name, brand_id, deleted) values (20, 'RAV4', 5, false);
insert into model (model_id, name, brand_id, deleted) values (21, 'Civic', 6, false);
insert into model (model_id, name, brand_id, deleted) values (22, 'Accord', 6, false);
insert into model (model_id, name, brand_id, deleted) values (23, 'CRV', 6, false);
insert into model (model_id, name, brand_id, deleted) values (24, 'Punto', 7, false);
insert into model (model_id, name, brand_id, deleted) values (25, 'Tipo', 7, false);
insert into model (model_id, name, brand_id, deleted) values (26, 'Bravo', 7, false);
insert into model (model_id, name, brand_id, deleted) values (27, 'Panda', 7, false);
insert into model (model_id, name, brand_id, deleted) values (28, 'Focus', 8, false);
insert into model (model_id, name, brand_id, deleted) values (29, 'Fiesta', 8, false);
insert into model (model_id, name, brand_id, deleted) values (30, 'Kuga', 8, false);
insert into model (model_id, name, brand_id, deleted) values (31, 'Raptor F150', 8, false);
insert into model (model_id, name, brand_id, deleted) values (32, 'Golf', 9, false);
insert into model (model_id, name, brand_id, deleted) values (33, 'Passat', 9, false);
insert into model (model_id, name, brand_id, deleted) values (34, 'Polo', 9, false);
insert into model (model_id, name, brand_id, deleted) values (35, 'Tiguan', 9, false);
insert into model (model_id, name, brand_id, deleted) values (36, 'Touran', 9, false);
insert into model (model_id, name, brand_id, deleted) values (37, 'Logan', 10, false);
insert into model (model_id, name, brand_id, deleted) values (38, 'Sandero', 10, false);
insert into model (model_id, name, brand_id, deleted) values (39, 'Duster', 10, false);

-- Vehicle Classes
insert into vehicle_class (vehicle_class_id, name, deleted) values (1, 'Micro Car', false);
insert into vehicle_class (vehicle_class_id, name, deleted) values (2, 'City Car', false);
insert into vehicle_class (vehicle_class_id, name, deleted) values (3, 'Minivan', false);
insert into vehicle_class (vehicle_class_id, name, deleted) values (4, 'Small Family', false);
insert into vehicle_class (vehicle_class_id, name, deleted) values (5, 'Large Family', false);
insert into vehicle_class (vehicle_class_id, name, deleted) values (6, 'Executive', false);
insert into vehicle_class (vehicle_class_id, name, deleted) values (7, 'Luxury Saloon', false);
insert into vehicle_class (vehicle_class_id, name, deleted) values (8, 'Sports Car', false);
insert into vehicle_class (vehicle_class_id, name, deleted) values (9, 'SUV', false);
insert into vehicle_class (vehicle_class_id, name, deleted) values (10, 'Old Timer', false);

-- Advertisements
insert into advertisement
(id, price, kilometers_per_day_limit, collision_damage_waiver, rating, description, location, owner_id, deleted)
values (1, 69.00, 240, false, 0, 'Opis 1', 'Teslic', 2, false);
insert into advertisement
(id, price, kilometers_per_day_limit, collision_damage_waiver, rating, description, location, owner_id, deleted)
values (2, 69.00, 220, true, 3.0, 'Opis 2', 'Kovilj', 2, false);
insert into advertisement
(id, price, kilometers_per_day_limit, collision_damage_waiver, rating, description, location, owner_id, deleted)
values (3, 69.00, 10, true, 4.2, 'Opis 3', 'Sremska Kamenica', 2, false);
insert into advertisement
(id, price, kilometers_per_day_limit, collision_damage_waiver, rating, description, location, owner_id, deleted)
values (4, 69.00, -1, false, 5.0, 'Opis 4', 'Mala Mostanica', 2, false);

-- Vehicles
insert into vehicle
(id, kilometers_traveled, child_seat_number, has_android, fuel_type_id, gearbox_type_id, vehicle_class_id, model_id, advertisement_id)
values (1, 15000, 2, true, 1, 1, 1, 1, 1);
insert into vehicle
(id, kilometers_traveled, child_seat_number, has_android, fuel_type_id, gearbox_type_id, vehicle_class_id, model_id, advertisement_id)
values (2, 2200, 1, false, 1, 2, 1, 2, 2);
insert into vehicle
(id, kilometers_traveled, child_seat_number, has_android, fuel_type_id, gearbox_type_id, vehicle_class_id, model_id, advertisement_id)
values (3, 5000, 2, true, 2, 1, 3, 1, 3);
insert into vehicle
(id, kilometers_traveled, child_seat_number, has_android, fuel_type_id, gearbox_type_id, vehicle_class_id, model_id, advertisement_id)
values (4, 1000, 1, true, 3, 2, 3, 4, 4);

-- Photos
insert into photo (id, path, advertisement_id) values (1, 'logan1.jpg', 1);
insert into photo (id, path, advertisement_id) values (2, 'logan2.jpg', 1);
insert into photo (id, path, advertisement_id) values (3, 'logan3.jpeg', 1);
insert into photo (id, path, advertisement_id) values (4, 'logan4.jpg', 1);
insert into photo (id, path, advertisement_id) values (5, 'logan1.jpg', 2);
insert into photo (id, path, advertisement_id) values (6, 'logan2.jpg', 2);
insert into photo (id, path, advertisement_id) values (7, 'logan3.jpeg', 2);
insert into photo (id, path, advertisement_id) values (8, 'logan4.jpg', 2);
insert into photo (id, path, advertisement_id) values (9, 'logan1.jpg', 3);
insert into photo (id, path, advertisement_id) values (10, 'logan2.jpg', 3);
insert into photo (id, path, advertisement_id) values (11, 'logan3.jpeg', 3);
insert into photo (id, path, advertisement_id) values (12, 'logan4.jpg', 3);
insert into photo (id, path, advertisement_id) values (13, 'logan1.jpg', 4);
insert into photo (id, path, advertisement_id) values (14, 'logan2.jpg', 4);
insert into photo (id, path, advertisement_id) values (15, 'logan3.jpeg', 4);
insert into photo (id, path, advertisement_id) values (16, 'logan4.jpg', 4);

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
insert into comment (title, content, allowed, user_id, advertisement_id) values ('First comment', 'This is first comment.', true, 3, 1);
insert into comment (title, content, allowed, user_id, advertisement_id) values ('Third comment', 'This is third comment.', true, 3, 2);
-- insert into comment (title, content, allowed, user_id, advertisement_id) values ('Sixth comment', 'This is sixth comment.', false, 3, 4);
-- insert into comment (title, content, allowed, user_id, advertisement_id) values ('Seventh comment', 'This is seventh comment.', false, 5, 4);
-- insert into comment (title, content, allowed, user_id, advertisement_id) values ('Eight comment', 'This is eighth comment.', false, 7, 4);
