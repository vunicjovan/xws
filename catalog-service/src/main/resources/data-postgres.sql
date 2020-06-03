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
insert into brand (deleted, name) values (false, 'BMW');
insert into brand (deleted, name) values (false, 'Audi');
insert into brand (deleted, name) values (false, 'Mercedes Benz');
insert into brand (deleted, name) values (false, 'Mazda');
insert into brand (deleted, name) values (false, 'Toyota');
insert into brand (deleted, name) values (false, 'Honda');
insert into brand (deleted, name) values (false, 'Fiat');
insert into brand (deleted, name) values (false, 'Ford');
insert into brand (deleted, name) values (false, 'Volkswagen');
