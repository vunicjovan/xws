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