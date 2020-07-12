CREATE DATABASE agent;
GRANT ALL PRIVILEGES ON DATABASE agent TO postgres;

CREATE SCHEMA account_schema AUTHORIZATION postgres;
CREATE SCHEMA agent_schema AUTHORIZATION postgres;
CREATE SCHEMA catalog_schema AUTHORIZATION postgres;
CREATE SCHEMA renting_schema AUTHORIZATION postgres;
CREATE SCHEMA search_schema AUTHORIZATION postgres;
CREATE SCHEMA view_schema AUTHORIZATION postgres;
CREATE SCHEMA message_schema AUTHORIZATION postgres;
