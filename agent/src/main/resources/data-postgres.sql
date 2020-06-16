-- Fuel Types
insert into fuel_type (name, deleted) values ('Gasoline', false);
insert into fuel_type (name, deleted) values ('Diesel', false);
insert into fuel_type (name, deleted) values ('Liquified Petroleum', false);
insert into fuel_type (name, deleted) values ('Natural Gas', false);
insert into fuel_type (name, deleted) values ('Ethanol', false);
insert into fuel_type (name, deleted) values ('Hydrogen', false);
insert into fuel_type (name, deleted) values ('Bio Diesel', false);
insert into fuel_type (name, deleted) values ('Electricity', false);

-- Gearbox Types
insert into gearbox_type (name, deleted) values ('Automatic', false);
insert into gearbox_type (name, deleted) values ('Manual', false);
insert into gearbox_type (name, deleted) values ('Semi-Automatic', false);
insert into gearbox_type (name, deleted) values ('Continously Variable', false);
insert into gearbox_type (name, deleted) values ('Dual Clutch', false);
insert into gearbox_type (name, deleted) values ('Direct Shift', false);

-- Vehicle Brands
insert into brand (deleted, name) values (false, 'BMW'); -- id = 1
insert into brand (deleted, name) values (false, 'Audi'); -- id = 2
insert into brand (deleted, name) values (false, 'Mercedes Benz'); -- id = 3
insert into brand (deleted, name) values (false, 'Mazda'); -- id = 4
insert into brand (deleted, name) values (false, 'Toyota'); -- id = 5
insert into brand (deleted, name) values (false, 'Honda'); -- id = 6
insert into brand (deleted, name) values (false, 'Fiat'); -- id = 7
insert into brand (deleted, name) values (false, 'Ford'); -- id = 8
insert into brand (deleted, name) values (false, 'Volkswagen'); -- id = 9
insert into brand (deleted, name) values (false, 'Dacia'); -- id = 10

-- Vehicle Models
insert into model (name, brand_id, deleted) values ('Series 3', 1, false);
insert into model (name, brand_id, deleted) values ('Series 5', 1, false);
insert into model (name, brand_id, deleted) values ('X3', 1, false);
insert into model (name, brand_id, deleted) values ('X5', 1, false);
insert into model (name, brand_id, deleted) values ('A4', 2, false);
insert into model (name, brand_id, deleted) values ('A6', 2, false);
insert into model (name, brand_id, deleted) values ('Q5', 2, false);
insert into model (name, brand_id, deleted) values ('Q7', 2, false);
insert into model (name, brand_id, deleted) values ('Class C', 3, false);
insert into model (name, brand_id, deleted) values ('Class E', 3, false);
insert into model (name, brand_id, deleted) values ('GLC', 3, false);
insert into model (name, brand_id, deleted) values ('GLA', 3, false);
insert into model (name, brand_id, deleted) values ('3', 4, false);
insert into model (name, brand_id, deleted) values ('6', 4, false);
insert into model (name, brand_id, deleted) values ('CX3', 4, false);
insert into model (name, brand_id, deleted) values ('CX5', 4, false);
insert into model (name, brand_id, deleted) values ('Auirs', 5, false);
insert into model (name, brand_id, deleted) values ('Yaris', 5, false);
insert into model (name, brand_id, deleted) values ('CHR', 5, false);
insert into model (name, brand_id, deleted) values ('RAV4', 5, false);
insert into model (name, brand_id, deleted) values ('Civic', 6, false);
insert into model (name, brand_id, deleted) values ('Accord', 6, false);
insert into model (name, brand_id, deleted) values ('CRV', 6, false);
insert into model (name, brand_id, deleted) values ('Punto', 7, false);
insert into model (name, brand_id, deleted) values ('Tipo', 7, false);
insert into model (name, brand_id, deleted) values ('Bravo', 7, false);
insert into model (name, brand_id, deleted) values ('Panda', 7, false);
insert into model (name, brand_id, deleted) values ('Focus', 8, false);
insert into model (name, brand_id, deleted) values ('Fiesta', 8, false);
insert into model (name, brand_id, deleted) values ('Kuga', 8, false);
insert into model (name, brand_id, deleted) values ('Raptor F150', 8, false);
insert into model (name, brand_id, deleted) values ('Golf', 9, false);
insert into model (name, brand_id, deleted) values ('Passat', 9, false);
insert into model (name, brand_id, deleted) values ('Polo', 9, false);
insert into model (name, brand_id, deleted) values ('Tiguan', 9, false);
insert into model (name, brand_id, deleted) values ('Touran', 9, false);
insert into model (name, brand_id, deleted) values ('Logan', 10, false);
insert into model (name, brand_id, deleted) values ('Sandero', 10, false);
insert into model (name, brand_id, deleted) values ('Duster', 10, false);

-- Vehicle Classes
insert into vehicle_class (name, deleted) values ('Micro Car', false);
insert into vehicle_class (name, deleted) values ('City Car', false);
insert into vehicle_class (name, deleted) values ('Minivan', false);
insert into vehicle_class (name, deleted) values ('Small Family', false);
insert into vehicle_class (name, deleted) values ('Large Family', false);
insert into vehicle_class (name, deleted) values ('Executive', false);
insert into vehicle_class (name, deleted) values ('Luxury Saloon', false);
insert into vehicle_class (name, deleted) values ('Sports Car', false);
insert into vehicle_class (name, deleted) values ('SUV', false);
insert into vehicle_class (name, deleted) values ('Old Timer', false);

-- Advertisements
insert into advertisement
(price, kilometers_per_day_limit, collision_damage_waiver, rating, description, location, owner_id)
values (25.00, 240, false, 0, 'Opis 1', 'Teslic', 1);
insert into advertisement
(price, kilometers_per_day_limit, collision_damage_waiver, rating, description, location, owner_id)
values (5.00, 220, true, 3.0, 'Opis 2', 'Kovilj', 1);
insert into advertisement
(price, kilometers_per_day_limit, collision_damage_waiver, rating, description, location, owner_id)
values (26.00, 10, true, 4.2, 'Opis 3', 'Sremska Kamenica', 1);
insert into advertisement
(price, kilometers_per_day_limit, collision_damage_waiver, rating, description, location, owner_id)
values (21.00, -1, false, 5.0, 'Opis 4', 'Mala Mostanica', 1);

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

-- Comments
insert into comment (title, content, allowed, user_id, advertisement_id) values ('First comment', 'This is first comment.', true, 3, 1);
insert into comment (title, content, allowed, user_id, advertisement_id) values ('Second comment', 'This is second comment.', true, 3, 1);
insert into comment (title, content, allowed, user_id, advertisement_id) values ('Third comment', 'This is third comment.', true, 3, 2);
insert into comment (title, content, allowed, user_id, advertisement_id) values ('Fourth comment', 'This is fourth comment.', true, 3, 2);
insert into comment (title, content, allowed, user_id, advertisement_id) values ('Fifth comment', 'This is fifth comment.', true, 3, 3);

