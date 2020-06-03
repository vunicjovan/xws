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